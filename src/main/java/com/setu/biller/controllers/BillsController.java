package com.setu.biller.controllers;

import com.setu.biller.dtos.CustomerIdentifiers;
import com.setu.biller.dtos.CustomerResponse;
import com.setu.biller.dtos.Error;
import com.setu.biller.dtos.ReceiptRequest;
import com.setu.biller.dtos.ReceiptResponse;
import com.setu.biller.dtos.SetuBillerResponse;
import com.setu.biller.services.CustomerService;
import com.setu.biller.services.ReceiptService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
class BillsController {

    @Autowired
    CustomerService customerService;

    @Autowired
    ReceiptService receiptService;

    @RequestMapping(value="/bills/fetch",method=RequestMethod.POST)
    public SetuBillerResponse fetch(@RequestBody CustomerIdentifiers customerIdentifiers) {
        SetuBillerResponse response = new SetuBillerResponse();
        CustomerResponse customerResponse = customerService.fetchCustomerInformation(customerIdentifiers);
        if(customerResponse == null){
            return notFoundCustomerResponse(response);
        }
        return foundResponse(response,customerResponse);
    }

    @RequestMapping(value="/bills/fetchReceipt",method=RequestMethod.POST)
    public SetuBillerResponse fetch(@RequestBody ReceiptRequest receipt) {
        System.out.println("RECEIPT : "+receipt.toString());
        SetuBillerResponse response = new SetuBillerResponse();
        ReceiptResponse receiptResponse = receiptService.saveCustomerReceipt(receipt);
        if(receiptResponse == null){
            return notFoundBillResponse(response);
        }
        return foundResponse(response,receiptResponse);
    }

    private SetuBillerResponse notFoundCustomerResponse(SetuBillerResponse response) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setSuccess(false);
        response.setError(new Error("customer-not-found", 
            "Customer not found",
            "The requested customer was not found in the biller system.", 
            "", ""));
        return response;
    }

    private SetuBillerResponse notFoundBillResponse(SetuBillerResponse response) {
        response.setStatus(HttpStatus.OK.value());
        response.setSuccess(true);
        response.setError(new Error("receipt-not-generated", 
            "Receipt not generated",
            "The requested bill was not found or is already paid in the biller system. It may also be because your payment amount issue", 
            "", ""));
        return response;
    }

    private SetuBillerResponse foundResponse(SetuBillerResponse response,Object data) {
        response.setStatus(HttpStatus.OK.value());
        response.setSuccess(true);
        response.setData(data);
        return response;
    }

}