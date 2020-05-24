package com.setu.biller.services;

import com.setu.biller.dtos.ReceiptRequest;
import com.setu.biller.dtos.ReceiptResponse;

public interface ReceiptService {
    
    ReceiptResponse saveCustomerReceipt(ReceiptRequest receiptRequest);

}