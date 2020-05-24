package com.setu.biller.dtos;

import java.util.Date;
import java.util.UUID;

import com.setu.biller.entities.Payment;
import com.setu.biller.entities.Receipt;

import org.bson.Document;

public class ReceiptGenerator {
    
    Document payment;
    Document receipt;

    public Document getPayment() {
        return payment;
    }

    public Document getReceipt() {
        return receipt;
    }

    public static class ReceiptGeneratorBuilder{

        ReceiptRequest receiptRequest;
        UUID uuid;

        public ReceiptGeneratorBuilder() {
        }

        public ReceiptGeneratorBuilder receiptRequest(ReceiptRequest receiptRequest){
            this.receiptRequest = receiptRequest;
            return this;
        }

        public ReceiptGenerator build(){
            ReceiptGenerator receiptGenerator = new ReceiptGenerator();
            setPaymentFromRequest(receiptGenerator);
            setReceiptFromRequest(receiptGenerator);
            return receiptGenerator;
        }

        private void setPaymentFromRequest(ReceiptGenerator receiptGenerator) {
            Document payment = new Document();
            payment.append("amountPaid",receiptRequest.getPaymentDetails().getAmountPaid().getValue());
            payment.append("billAmount",receiptRequest.getPaymentDetails().getBillAmount().getValue());
            payment.append("billerBillID",receiptRequest.getBillerBillID());
            payment.append("platformBillID",receiptRequest.getPlatformBillID());
            payment.append("platformTransactionRefID",receiptRequest.getPaymentDetails().getPlatformTransactionRefID());
            payment.append("uniquePaymentRefID",receiptRequest.getPaymentDetails().getUniquePaymentRefID());
            uuid = UUID.randomUUID();
            payment.append("_id",uuid.toString());
            receiptGenerator.payment = payment;
        }

        private void setReceiptFromRequest(ReceiptGenerator receiptGenerator) {
            Document receipt = new Document();
            receipt.append("billerBillID",receiptRequest.getBillerBillID());
            receipt.append("platformBillID",receiptRequest.getPlatformBillID());
            receipt.append("platformTransactionRefID",receiptRequest.getPaymentDetails().getPlatformTransactionRefID());
            receipt.append("date",new Date());
            uuid = UUID.randomUUID();
            receipt.append("_id",uuid.toString());
            receiptGenerator.receipt = receipt;
        }
        
    }

}