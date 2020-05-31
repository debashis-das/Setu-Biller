package com.setu.biller.dtos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import com.setu.biller.entities.Bill;
import com.setu.biller.entities.Customer;
import com.setu.biller.entities.Order;

public class BillResponse {

    // Customer object
    CustomerDto customer;
    CustomerAccount customerAccount;
    // Order Object
    Object aggregates;
    Object[] discounts;
    Object[] fees;
    Object[] items;
    Object[] taxes;
    String paymentStatus;
    // Bill Object
    String billerBillID;
    String generatedOn;
    String dueDate;
    String recurrence;
    String billType;
    String billerCategory;
    String billerProductInstanceID;
    String amountExactness;

    

    public static class BillResponseBuilder {

        Customer customer;
        Order order;
        Bill bill;

        public BillResponseBuilder() {
        }

        public BillResponseBuilder customer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public BillResponseBuilder order(Order order) {
            this.order = order;
            return this;
        }

        public BillResponseBuilder bill(Bill bill) {
            this.bill = bill;
            return this;
        }

        public BillResponse build() {
            BillResponse billResponse = new BillResponse();
            setCustomerAttributes(billResponse);
            setOrderAttributes(billResponse);
            setBillAttributes(billResponse);
            return billResponse;
        }

        private void setCustomerAttributes(BillResponse billResponse) {
            CustomerDto customerDto = new CustomerDto();
            customerDto.setName(customer.getName());
            CustomerAccount customerAccount = new CustomerAccount();
            customerAccount.setId(customer.get_id());
            billResponse.customer = customerDto;
            billResponse.customerAccount = customerAccount;
        }

        private void setOrderAttributes(BillResponse billResponse) {
            billResponse.aggregates = order.getAggregates();
            billResponse.discounts = order.getDiscounts();
            billResponse.fees = order.getFees();
            billResponse.items = order.getItems();
            billResponse.taxes = order.getTaxes();
        }

        private void setBillAttributes(BillResponse billResponse) {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            billResponse.billerBillID = bill.getBillerBillID();
            billResponse.generatedOn = formatter.format(bill.getGeneratedOn());
            billResponse.dueDate = formatter.format(bill.getDueDate());
            billResponse.recurrence = bill.getRecurrence();
            billResponse.billType = bill.getBillType();
            billResponse.billerCategory = bill.getBillerCategory();
            billResponse.billerProductInstanceID = bill.getBillerProductInstanceID();
            billResponse.amountExactness = bill.getAmountExactness().toString();
        }

    }

    @Override
    public String toString() {
        return "BillResponse [aggregates=" + aggregates + ", amountExactness=" + amountExactness + ", billType="
                + billType + ", billerBillID=" + billerBillID + ", billerCategory=" + billerCategory
                + ", billerProductInstanceID=" + billerProductInstanceID + ", customer=" + customer
                + ", customerAccount=" + customerAccount + ", discounts=" + Arrays.toString(discounts) + ", dueDate="
                + dueDate + ", fees=" + Arrays.toString(fees) + ", generatedOn=" + generatedOn + ", items="
                + Arrays.toString(items) + ", paymentStatus=" + paymentStatus + ", recurrence=" + recurrence
                + ", taxes=" + Arrays.toString(taxes) + "]";
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public CustomerAccount getCustomerAccount() {
        return customerAccount;
    }

    public Object getAggregates() {
        return aggregates;
    }

    public Object[] getDiscounts() {
        return discounts;
    }

    public Object[] getFees() {
        return fees;
    }

    public Object[] getItems() {
        return items;
    }

    public Object[] getTaxes() {
        return taxes;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getBillerBillID() {
        return billerBillID;
    }

    public String getGeneratedOn() {
        return generatedOn;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getRecurrence() {
        return recurrence;
    }

    public String getBillType() {
        return billType;
    }

    public String getBillerCategory() {
        return billerCategory;
    }

    public String getBillerProductInstanceID() {
        return billerProductInstanceID;
    }

    public String getAmountExactness() {
        return amountExactness;
    }    

}