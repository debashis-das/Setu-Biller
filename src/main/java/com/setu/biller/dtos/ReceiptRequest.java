package com.setu.biller.dtos;

public class ReceiptRequest {
    
    String billerBillID;
    String platformBillID;
    PaymentDetails paymentDetails;

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

    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    @Override
    public String toString() {
        return "ReceiptRequest [billerBillID=" + billerBillID + ", paymentDetails=" + paymentDetails
                + ", platformBillID=" + platformBillID + "]";
    }

}