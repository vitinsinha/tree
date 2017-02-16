package com.ncr.model;

import org.springframework.http.HttpStatus;

/**
 * Created by SINHAVI on 12/31/2016.
 */
public class ResponseType {

    private HttpStatus responseCode;
    private String responseStatus;
    private String responseDescription;

    public ResponseType() {
    }

    public ResponseType(HttpStatus responseCode, String responseStatus, String responseDescription) {
        this.responseCode = responseCode;
        this.responseStatus = responseStatus;
        this.responseDescription = responseDescription;
    }

    public HttpStatus getResponseCode() {
        return this.responseCode;
    }

    public void setResponseCode(HttpStatus responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseStatus() {
        return this.responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getResponseDescription() {
        return this.responseDescription;
    }

    public void setResponseDescription(String responseDescription) {
        this.responseDescription = responseDescription;
    }

}
