package com.setu.biller.dtos;

import java.util.List;

public class BillDetails {

    BillFetchStatus billFetchStatus;
    List<BillResponse> bills;

    public BillFetchStatus getBillFetchStatus() {
        return billFetchStatus;
    }

    public void setBillFetchStatus(BillFetchStatus billFetchStatus) {
        this.billFetchStatus = billFetchStatus;
    }

    public List<BillResponse> getBills() {
        return bills;
    }

    public void setBills(List<BillResponse> bills) {
        this.bills = bills;
    }

    @Override
    public String toString() {
        return "BillDetails [billFetchStatus=" + billFetchStatus + ", bills=" + bills + "]";
    }

}