package com.setu.biller.helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.util.ResourceUtils;

@Configuration
public class OnboardSetu {

    @Bean
    @Scope(value = "singleton")
    public JsonArray getCustomerParameter(){
        JsonParser parser = new JsonParser();
        try{
            File onboard = ResourceUtils.getFile("classpath:onboard.json");
            JsonArray parameters = parser.parse(new FileReader(onboard)).getAsJsonObject().get("customerParameters").getAsJsonArray();
            return parameters;
        } catch (IOException e) {
            e.printStackTrace();
        } 
        return null;
    }
}