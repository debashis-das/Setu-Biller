package com.setu.biller.helpers;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class OnboardSetu {

    private String onboard = "{\"customerParameters\":[{\"attributeName\":\"mobileNumber\",\"displayName\":\"Mobile number\",\"dataType\":\"string\",\"minLength\":10,\"maxLength\":10,\"isMandatory\":true},{\"attributeName\":\"phoneNumber\",\"displayName\":\"Phone number\",\"dataType\":\"string\",\"minLength\":10,\"maxLength\":10,\"isMandatory\":false},{\"attributeName\":\"mail\",\"displayName\":\"Email Id\",\"dataType\":\"string\",\"minLength\":5,\"maxLength\":50,\"isMandatory\":false}]}";

    @Bean
    @Scope(value = "singleton")
    public JsonArray getCustomerParameter(){
        JsonParser parser = new JsonParser();
        JsonArray parameters = parser.parse(onboard).getAsJsonObject().get("customerParameters").getAsJsonArray();
        return parameters;
    }
}