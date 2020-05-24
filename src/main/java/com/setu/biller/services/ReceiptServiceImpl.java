package com.setu.biller.services;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import com.setu.biller.dtos.ReceiptDto;
import com.setu.biller.dtos.ReceiptGenerator;
import com.setu.biller.dtos.ReceiptRequest;
import com.setu.biller.dtos.ReceiptResponse;
import com.setu.biller.entities.AmountExactness;
import com.setu.biller.entities.Bill;
import com.setu.biller.entities.Order;
import com.setu.biller.entities.Payment;
import com.setu.biller.entities.PaymentStatus;
import com.setu.biller.helpers.SetuMongoClient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceiptServiceImpl implements ReceiptService {

    @Autowired
    Gson gson;

    protected final Log logger = LogFactory.getLog(getClass());

    @Override
    public ReceiptResponse saveCustomerReceipt(final ReceiptRequest receiptRequest) {
        final MongoCollection billCollection = SetuMongoClient.getInstance().getMongoCollection("bill");
        final MongoCollection orderCollection = SetuMongoClient.getInstance().getMongoCollection("order");
        Bill bill = fetchBillFromReceipt(receiptRequest,billCollection);
        Order order = fetchOrderFromReceipt(receiptRequest,orderCollection);
        logger.info("BILL : "+bill.toString());
        logger.info("Order : "+order.toString());
        if (bill != null && order != null && order.getPaymentStatus() != PaymentStatus.PAID) {
            final MongoCollection paymentCollection = SetuMongoClient.getInstance().getMongoCollection("payment");
            final List<Payment> payments = fetchPreviousPayments(receiptRequest, paymentCollection);
            for(Payment temp : payments){
                logger.info(temp.toString());
            }
            if (bill.getAmountExactness().equals(AmountExactness.EXACT)) {
                return manageIfAmountExact(receiptRequest, billCollection, orderCollection, paymentCollection,
                        payments);
            } else {
                return manageIfAmountNotExact(receiptRequest, billCollection, orderCollection, paymentCollection,
                        payments);
            }
        }
        return null;
    }

    private List<Payment> fetchPreviousPayments(final ReceiptRequest receiptRequest, final MongoCollection paymentCollection) {
        final BasicDBObject queryPayment = new BasicDBObject();
        queryPayment.append("billerBillID", receiptRequest.getBillerBillID());
        final List<Payment> payments = new ArrayList<>();
        try (MongoCursor<Document> cursor = paymentCollection.find(queryPayment).iterator()) {
            while (cursor.hasNext()) {
                payments.add(gson.fromJson(cursor.next().toJson(), Payment.class));
            }
        }
        return payments;
    }

    private Bill fetchBillFromReceipt(ReceiptRequest receiptRequest,MongoCollection billCollection){
        Bill bill = null;
        final BasicDBObject queryBill = new BasicDBObject();
        queryBill.append("billerBillID", receiptRequest.getBillerBillID());
        
        try (MongoCursor<Document> cursor = billCollection.find(queryBill).iterator()) {
            while (cursor.hasNext()) {
                bill = gson.fromJson(cursor.next().toJson(), Bill.class);
            }
        }
        return bill;
    }

    private Order fetchOrderFromReceipt(ReceiptRequest receiptRequest,MongoCollection orderCollection){
        Order order = null;
        final BasicDBObject queryOrder = new BasicDBObject();
        queryOrder.append("billerBillID", receiptRequest.getBillerBillID());
        try (MongoCursor<Document> cursor = orderCollection.find(queryOrder).iterator()) {
            while (cursor.hasNext()) {
                order = gson.fromJson(cursor.next().toJson(), Order.class);
            }
        }
        return order;
    }

    private ReceiptResponse manageIfAmountNotExact(final ReceiptRequest receiptRequest,
            final MongoCollection billCollection, final MongoCollection orderCollection,
            final MongoCollection paymentCollection, final List<Payment> payments) {
        
        double amountAlreadyPaid = payments.stream().mapToDouble(payment -> payment.getAmountPaid()).sum();
        amountAlreadyPaid += receiptRequest.getPaymentDetails().getAmountPaid().getValue();

        if (amountAlreadyPaid <= receiptRequest.getPaymentDetails().getBillAmount().getValue()) {
            if(amountAlreadyPaid == receiptRequest.getPaymentDetails().getBillAmount().getValue()){
                setPaymentStatusInBillAndOrder(receiptRequest, billCollection, orderCollection);
            }
            return createPaymentAndReceipt(receiptRequest, paymentCollection);
        }else{
            return null;
        } 
    }

    private ReceiptResponse manageIfAmountExact(final ReceiptRequest receiptRequest,
            final MongoCollection billCollection, final MongoCollection orderCollection,
            final MongoCollection paymentCollection, final List<Payment> payments) {
        if (receiptRequest.getPaymentDetails().getAmountPaid().getValue()
                 == receiptRequest.getPaymentDetails().getBillAmount().getValue()) {
            setPaymentStatusInBillAndOrder(receiptRequest, billCollection, orderCollection);
            logger.info("manageIfAmountExact : ");
            if (payments.size() == 0) {
                return createPaymentAndReceipt(receiptRequest, paymentCollection);
            }
        }
        return null;
    }

    private ReceiptResponse createPaymentAndReceipt(final ReceiptRequest receiptRequest,
            final MongoCollection paymentCollection) {
        final ReceiptGenerator receiptGenerator = new ReceiptGenerator.ReceiptGeneratorBuilder()
                .receiptRequest(receiptRequest).build();
        paymentCollection.insertOne(receiptGenerator.getPayment());
        final MongoCollection receiptCollection = SetuMongoClient.getInstance().getMongoCollection("receipt");
        receiptCollection.insertOne(receiptGenerator.getPayment());
        return createReceiptResponse(receiptRequest, receiptGenerator);
    }

    private ReceiptResponse createReceiptResponse(final ReceiptRequest receiptRequest,
            final ReceiptGenerator receiptGenerator) {
        final ReceiptResponse receiptResponse = new ReceiptResponse();
        receiptResponse.setBillerBillID(receiptRequest.getBillerBillID());
        receiptResponse.setPlatformBillID(receiptRequest.getPlatformBillID());
        receiptResponse.setPlatformTransactionRefID(receiptRequest.getPaymentDetails().getPlatformTransactionRefID());
        final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        receiptResponse.setReceipt(new ReceiptDto(receiptGenerator.getReceipt().getString("_id"),
                formatter.format(receiptGenerator.getReceipt().getDate("date"))));
        return receiptResponse;
    }

    private void setPaymentStatusInBillAndOrder(final ReceiptRequest receiptRequest,
            final MongoCollection billCollection, final MongoCollection orderCollection) {
        final Bson filterOrder = Filters.eq("billerBillID", receiptRequest.getBillerBillID());
        final Bson updateOrder = new Document("$set", new Document("paymentStatus", PaymentStatus.PAID.toString()));
        UpdateResult orderresult = orderCollection.updateOne(filterOrder, updateOrder);
        logger.info("setPaymentStatusInBillAndOrder : "+orderresult.toString());
        final Bson filterBill = Filters.eq("billerBillID", receiptRequest.getBillerBillID());
        final Bson updateBill = new Document("$set", new Document("paymentStatus", PaymentStatus.PAID.toString()));
        UpdateResult billresult = billCollection.updateOne(filterBill,updateBill);
        logger.info("setPaymentStatusInBillAndOrder : "+billresult.toString());
    }
    
}