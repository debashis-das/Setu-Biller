package com.setu.biller.entities;

import java.util.Date;

public class Receipt {

    String _id;
    Date receiptDate;
    String billerBillID;
    String platformBillID;
    String platformTransactionRefID;

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

    public Date getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(Date receiptDate) {
        this.receiptDate = receiptDate;
    }

    @Override
    public String toString() {
        return "Receipt [_id=" + _id + ", billerBillID=" + billerBillID + ", platformBillID=" + platformBillID
                + ", platformTransactionRefID=" + platformTransactionRefID + ", receiptDate=" + receiptDate + "]";
    }

}