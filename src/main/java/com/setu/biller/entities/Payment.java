package com.setu.biller.entities;

import java.math.BigDecimal;

public class Payment {

    String _id;
    String billerBillID;
    String platformBillID;
    String platformTransactionRefID;
    String uniquePaymentRefID;
    double amountPaid;
    double billAmount;

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

    public String getPlatformBillID() {
        return platformBillID;
    }

    public void setPlatformBillID(String platformBillID) {
        this.platformBillID = platformBillID;
    }

    public String getPlatformTransactionRefID() {
        return platformTransactionRefID;
    }

    public void setPlatformTransactionRefID(String platformTransactionRefID) {
        this.platformTransactionRefID = platformTransactionRefID;
    }

    public String getUniquePaymentRefID() {
        return uniquePaymentRefID;
    }

    public void setUniquePaymentRefID(String uniquePaymentRefID) {
        this.uniquePaymentRefID = uniquePaymentRefID;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public double getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(double billAmount) {
        this.billAmount = billAmount;
    }

    @Override
    public String toString() {
        return "Payment [_id=" + _id + ", amountPaid=" + amountPaid + ", billAmount=" + billAmount + ", billerBillID="
                + billerBillID + ", platformBillID=" + platformBillID + ", platformTransactionRefID="
                + platformTransactionRefID + ", uniquePaymentRefID=" + uniquePaymentRefID + "]";
    }
    
}