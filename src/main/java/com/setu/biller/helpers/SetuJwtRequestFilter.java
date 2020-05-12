package com.setu.biller.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.exceptions.JWTVerificationException;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class SetuJwtRequestFilter extends OncePerRequestFilter {

    private SetuJwtHelper setuJwtHelper;

    private void setup(){
        Properties properties = new Properties();
        try {
            File file = ResourceUtils.getFile("classpath:biller-auth.properties");
            InputStream in = new FileInputStream(file);
            properties.load(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setuJwtHelper = new SetuJwtHelper(properties.getProperty("secret"), properties.getProperty("schemedId"));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        setup();
        System.out.println("HERE---------------->");
        final String requestTokenHeader = request.getHeader("Authorization");
        System.out.println("TOKEN---------------->"+requestTokenHeader);
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			try {
				setuJwtHelper.verifyBearerToken(requestTokenHeader);
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
			} catch (JWTVerificationException e) {
				System.out.println("JWT Token has expired");
			}
		} else {
			logger.warn("JWT Token does not begin with Bearer String");
        }
        chain.doFilter(request, response);
    }
}