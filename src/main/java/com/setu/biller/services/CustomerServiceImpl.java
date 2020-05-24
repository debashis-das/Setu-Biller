package com.setu.biller.services;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.setu.biller.dtos.BillDetails;
import com.setu.biller.dtos.BillFetchStatus;
import com.setu.biller.dtos.BillResponse;
import com.setu.biller.dtos.CustomerDto;
import com.setu.biller.dtos.CustomerIdentifier;
import com.setu.biller.dtos.CustomerIdentifiers;
import com.setu.biller.dtos.CustomerResponse;
import com.setu.biller.entities.Bill;
import com.setu.biller.entities.Customer;
import com.setu.biller.entities.Order;
import com.setu.biller.entities.PaymentStatus;
import com.setu.biller.helpers.OnboardSetu;
import com.setu.biller.helpers.SetuMongoClient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    Gson gson;

    @Autowired
    private OnboardSetu onboardSetu;

    protected final Log logger = LogFactory.getLog(getClass());

    @Override
    public CustomerResponse fetchCustomerInformation(final CustomerIdentifiers customerIdentifiers) {
        final JsonArray parameters = onboardSetu.getCustomerParameter();
        if (!validateParamters(parameters, customerIdentifiers)) {
            logger.info("validation failed");
            return null;
        } else {
            final List<Customer> customers = setCustomerIfFound(customerIdentifiers);
            if (customers.size() != 0) {
                final Customer customer = customers.get(0);
                final CustomerResponse response = setCustomerInResponse(customer);
                final BillDetails billDetails = setBillDetailsOfCustomer(customer);
                response.setBillDetails(billDetails);
                return response;
            } else {
                return null;
            }
        }
    }

    private BillDetails setBillDetailsOfCustomer(Customer customer) {
        final BillDetails billDetails = new BillDetails();
        final List<BillResponse> billResponses = new ArrayList<>();
        final List<Order> orders = setUnPaidOrdersOfCustomer(customer);
        if (orders.size() != 0) {
            billDetails.setBillFetchStatus(BillFetchStatus.AVAILABLE);
            setUnPaidBillsOfCustomer(customer, orders, billResponses);
            billDetails.setBills(billResponses);
        } else {
            billDetails.setBillFetchStatus(BillFetchStatus.NO_OUTSTANDING);
            billDetails.setBills(billResponses);
        }
        return billDetails;
    }

    private List<Customer> setCustomerIfFound(final CustomerIdentifiers customerIdentifiers) {
        List<Customer> customers = new ArrayList<>();
        MongoCollection customerCollection = SetuMongoClient.getInstance().getMongoCollection("customer");
        BasicDBObject query = new BasicDBObject();
        for (final CustomerIdentifier temp : customerIdentifiers.getCustomerIdentifiers()) {
            query.append(temp.getAttributeName(), temp.getAttributeValue());
        }

        try (MongoCursor<Document> cursor = customerCollection.find(query).iterator()) {
            while (cursor.hasNext()) {
                customers.add(gson.fromJson(cursor.next().toJson(), Customer.class));
            }
        }
        return customers;
    }

    private List<Order> setUnPaidOrdersOfCustomer(final Customer customer) {
        final MongoCollection orderCollection = SetuMongoClient.getInstance().getMongoCollection("order");
        final BasicDBObject queryOrder = new BasicDBObject();
        queryOrder.append("customerId", customer.get_id());
        queryOrder.append("paymentStatus", PaymentStatus.NOT_PAID.toString());
        final List<Order> orders = new ArrayList<>();
        try (MongoCursor<Document> cursor = orderCollection.find(queryOrder).iterator()) {
            while (cursor.hasNext()) {
                orders.add(gson.fromJson(cursor.next().toJson(), Order.class));
            }
        }
        return orders;
    }

    private void setUnPaidBillsOfCustomer(final Customer customer, final List<Order> orders,
            final List<BillResponse> billResponses) {
        for (final Order order : orders) {
            logger.info("Order : " + orders.toString());
            final MongoCollection billCollection = SetuMongoClient.getInstance().getMongoCollection("bill");
            final BasicDBObject queryBill = new BasicDBObject();
            queryBill.append("orderId", order.get_id());
            try (MongoCursor<Document> cursor = billCollection.find(queryBill).iterator()) {
                while (cursor.hasNext()) {
                    final Bill bill = gson.fromJson(cursor.next().toJson(), Bill.class);
                    billResponses.add(
                            new BillResponse.BillResponseBuilder().customer(customer).order(order).bill(bill).build());
                }
            }
        }
    }

    private CustomerResponse setCustomerInResponse(final Customer customer) {
        logger.info("Customer : " + customer);
        final CustomerDto cust = new CustomerDto();
        cust.setName(customer.getName());
        final CustomerResponse response = new CustomerResponse();
        response.setCustomer(cust);
        return response;
    }

    

}