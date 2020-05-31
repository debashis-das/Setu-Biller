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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTraceID() {
        return traceID;
    }

    public void setTraceID(String traceID) {
        this.traceID = traceID;
    }

    public String getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(String docUrl) {
        this.docUrl = docUrl;
    }

}