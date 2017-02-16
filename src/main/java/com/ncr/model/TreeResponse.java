package com.ncr.model;

import org.springframework.http.HttpStatus;

/**
 * Created by SINHAVI on 12/31/2016.
 */
public class TreeResponse<T> {
    private ResponseType responseType;
    private T data;

    public ResponseType getResponseType() {
        return responseType;
    }
    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
}
