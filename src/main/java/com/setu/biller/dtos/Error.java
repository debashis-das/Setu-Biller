package com.setu.biller.dtos;

public class Error {

    String code="";
    String title="";
    String detail="";
    String traceID="";
    String docUrl="";

    public Error(String code, String title, String detail, String traceID, String docUrl) {
        this.code = code;
        this.title = title;
        this.detail = detail;
        this.traceID = traceID;
        this.docUrl = docUrl;
    }

}