package com.setu.biller.entities;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Customer {

    @JsonProperty
    String customerName;

    @JsonProperty
    CustomerIdentifiers[] customerIdentifiers;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public CustomerIdentifiers[] getCustomerIdentifiers() {
        return customerIdentifiers;
    }

    public void setCustomerIdentifiers(CustomerIdentifiers[] customerIdentifiers) {
        this.customerIdentifiers = customerIdentifiers;
    }

    @Override
    public String toString() {
        return "Customer [customerIdentifiers=" + Arrays.toString(customerIdentifiers) + ", customerName="
                + customerName + "]";
    }

}