package com.setu.biller.entities;

import com.google.gson.annotations.SerializedName;

public enum PaymentStatus {
    @SerializedName("NOT_PAID")NOT_PAID,
    @SerializedName("PAID")PAID
}