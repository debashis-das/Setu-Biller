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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class SetuJwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    Environment environment;

    private SetuJwtHelper setuJwtHelper;

    private void setup(){
        setuJwtHelper = new SetuJwtHelper(environment.getProperty("secret"), environment.getProperty("schemedId"));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        setup();
        final String requestTokenHeader = request.getHeader("Authorization");
        logger.info("TOKEN : "+requestTokenHeader);
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			try {
                setuJwtHelper.verifyBearerToken(requestTokenHeader);
                chain.doFilter(request, response);
                return;
			} catch (IllegalArgumentException e) {
                logger.warn("Unable to get JWT Token");
			} catch (JWTVerificationException e) {
                logger.warn("JWT Token has expired");
			}
		} else {
            logger.warn("JWT Token does not begin with Bearer String");
        }
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}