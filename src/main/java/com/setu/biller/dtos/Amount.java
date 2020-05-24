package com.setu.biller.dtos;

import java.math.BigDecimal;

public class Amount {
    
    double value;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Amount [value=" + value + "]";
    }
}