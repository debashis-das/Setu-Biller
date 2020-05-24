package com.setu.biller.entities;

import java.util.Arrays;

public class Order {

    String _id;
    String customerId;
    Object aggregates;
    Object[] discounts;
    Object[] fees;
    Object[] items;
    Object[] taxes;
    PaymentStatus paymentStatus;
    String billerBillID;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Object getAggregates() {
        return aggregates;
    }

    public void setAggregates(Object aggregates) {
        this.aggregates = aggregates;
    }

    public Object[] getDiscounts() {
        return discounts;
    }

    public void setDiscounts(Object[] discounts) {
        this.discounts = discounts;
    }

    public Object[] getFees() {
        return fees;
    }

    public void setFees(Object[] fees) {
        this.fees = fees;
    }

    public Object[] getItems() {
        return items;
    }

    public void setItems(Object[] items) {
        this.items = items;
    }

    public Object[] getTaxes() {
        return taxes;
    }

    public void setTaxes(Object[] taxes) {
        this.taxes = taxes;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getBillerBillID() {
        return billerBillID;
    }

    public void setBillerBillID(String billerBillID) {
        this.billerBillID = billerBillID;
    }

    @Override
    public String toString() {
        return "Order [_id=" + _id + ", aggregates=" + aggregates + ", billerBillID=" + billerBillID + ", customerId="
                + customerId + ", discounts=" + Arrays.toString(discounts) + ", fees=" + Arrays.toString(fees)
                + ", items=" + Arrays.toString(items) + ", paymentStatus=" + paymentStatus + ", taxes="
                + Arrays.toString(taxes) + "]";
    }

}