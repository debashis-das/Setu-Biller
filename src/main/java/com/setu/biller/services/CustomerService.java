package com.setu.biller.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.setu.biller.dtos.CustomerIdentifier;
import com.setu.biller.dtos.CustomerIdentifiers;
import com.setu.biller.dtos.CustomerResponse;
import com.setu.biller.dtos.ReceiptRequest;
import com.setu.biller.dtos.ReceiptResponse;

public interface CustomerService {
    
    CustomerResponse fetchCustomerInformation(CustomerIdentifiers customerIdentifiers);

    default boolean validateParamters(JsonArray parameters, CustomerIdentifiers parametervalues){
        for(CustomerIdentifier temp : parametervalues.getCustomerIdentifiers()){
            for(JsonElement parameter : parameters){
                JsonObject param = parameter.getAsJsonObject();
                System.out.println("CustomerIdentifier : "+temp);
                System.out.println("parameter : "+param.get("minLength").getAsInt() + " : "+param.get("maxLength").getAsInt());
                if(param.get("isMandatory").getAsBoolean() && !(param.get("attributeName").getAsString().equals(temp.getAttributeName())
                && temp.getAttributeValue().length() >= param.get("minLength").getAsInt() 
                && temp.getAttributeValue().length() <= param.get("maxLength").getAsInt())){
                    return false;
                }
            }
        }
        return true;
    }
}