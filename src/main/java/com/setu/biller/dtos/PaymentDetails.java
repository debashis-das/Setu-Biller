package com.setu.biller.dtos;

public class PaymentDetails {
    
    String platformTransactionRefID;
    String uniquePaymentRefID;
    Amount amountPaid;
    Amount billAmount;

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

    public Amount getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Amount amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Amount getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(Amount billAmount) {
        this.billAmount = billAmount;
    }

    @Override
    public String toString() {
        return "PaymentDetails [amountPaid=" + amountPaid + ", billAmount=" + billAmount + ", platformTransactionRefID="
                + platformTransactionRefID + ", uniquePaymentRefID=" + uniquePaymentRefID + "]";
    }

}