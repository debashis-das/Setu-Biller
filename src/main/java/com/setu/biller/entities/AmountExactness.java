package com.setu.biller.entities;

import com.google.gson.annotations.SerializedName;

public enum AmountExactness {
    @SerializedName("EXACT")EXACT,
    @SerializedName("RECURRENCE")RECURRENCE
}