package com.setu.biller.entities;

import java.util.Date;

public class Bill {

    String _id;
    String billerBillID;
    Date generatedOn;
    Date dueDate;
    String recurrence;
    String billType;
    String billerCategory;
    String billerProductInstanceID;
    AmountExactness amountExactness;
    String orderId;
    PaymentStatus paymentStatus;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getBillerBillID() {
        return billerBillID;
    }

    public void setBillerBillID(String billerBillID) {
        this.billerBillID = billerBillID;
    }

    public Date getGeneratedOn() {
        return generatedOn;
    }

    public void setGeneratedOn(Date generatedOn) {
        this.generatedOn = generatedOn;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(String recurrence) {
        this.recurrence = recurrence;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getBillerCategory() {
        return billerCategory;
    }

    public void setBillerCategory(String billerCategory) {
        this.billerCategory = billerCategory;
    }

    public String getBillerProductInstanceID() {
        return billerProductInstanceID;
    }

    public void setBillerProductInstanceID(String billerProductInstanceID) {
        this.billerProductInstanceID = billerProductInstanceID;
    }

    public AmountExactness getAmountExactness() {
        return amountExactness;
    }

    public void setAmountExactness(AmountExactness amountExactness) {
        this.amountExactness = amountExactness;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public String toString() {
        return "Bill [_id=" + _id + ", amountExactness=" + amountExactness + ", billType=" + billType
                + ", billerBillID=" + billerBillID + ", billerCategory=" + billerCategory + ", billerProductInstanceID="
                + billerProductInstanceID + ", dueDate=" + dueDate + ", generatedOn=" + generatedOn + ", orderId="
                + orderId + ", paymentStatus=" + paymentStatus + ", recurrence=" + recurrence + "]";
    }

}