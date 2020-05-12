package com.setu.biller.entities;

public class CustomerIdentifiers {
    
    String attributeName;
    String attributeValue;

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    @Override
    public String toString() {
        return "CustomerIdentifiers [attributeName=" + attributeName + ", attributeValue=" + attributeValue + "]";
    }

}