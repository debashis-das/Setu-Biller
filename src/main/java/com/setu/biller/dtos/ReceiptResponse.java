package com.setu.biller.dtos;

public class ReceiptResponse {
    String billerBillID;
    String platformBillID;
    String platformTransactionRefID;
    ReceiptDto receipt;

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

    public ReceiptDto getReceipt() {
        return receipt;
    }

    public void setReceipt(ReceiptDto receipt) {
        this.receipt = receipt;
    }

    @Override
    public String toString() {
        return "ReceiptResponse [billerBillID=" + billerBillID + ", platformBillID=" + platformBillID
                + ", platformTransactionRefID=" + platformTransactionRefID + ", receipt=" + receipt + "]";
    }
    
}