package com.ncr.util;

import com.ncr.model.Node;
import com.ncr.model.ResponseType;
import com.ncr.model.TreeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static com.ncr.common.ApiConstants.FAILURE;
import static com.ncr.common.ApiConstants.SUCCESS;
import static com.ncr.common.ApiConstants.SUCCESS_DESCRIPTION;

/**
 * Created by SINHAVI on 12/31/2016.
 */

@Component
public class TreeHelper {

    private static final Logger LOG = LoggerFactory.getLogger(TreeHelper.class);

    public void printTree(Node node, String appender) {
        System.out.println(appender + node.getName());
        for (Node each : node.getChildren()) {
            printTree(each, appender + appender);
        }
    }

    public <T> ResponseEntity<TreeResponse<T>> createResponse(T body, HttpStatus httpStatusCode,
                                                              String responseStatus, String responseDescription) {
        TreeResponse<T> response = new TreeResponse<T>();
        response.setData(body);
        ResponseType responseType = new ResponseType();
        responseType.setResponseCode(httpStatusCode);
        responseType.setResponseStatus(responseStatus);
        responseType.setResponseDescription(responseDescription);
        response.setResponseType(responseType);

        return new ResponseEntity(response, httpStatusCode);
    }

    public <T> ResponseEntity<TreeResponse<T>> createErrorResponse(T body, String errorMessage) {
        TreeResponse<T> response = new TreeResponse<T>();
        response.setData(body);
        ResponseType responseType = new ResponseType();
        responseType.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR);
        responseType.setResponseDescription(errorMessage);
        responseType.setResponseStatus(FAILURE);
        response.setResponseType(responseType);

        return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public <T> ResponseEntity<TreeResponse<T>> createSuccessResponse(T body) {
        TreeResponse<T> response = new TreeResponse<T>();
        response.setData(body);
        ResponseType responseType = new ResponseType();
        responseType.setResponseCode(HttpStatus.OK);
        responseType.setResponseStatus(SUCCESS);
        responseType.setResponseDescription(SUCCESS_DESCRIPTION);
        response.setResponseType(responseType);

        return new ResponseEntity(response, HttpStatus.OK);
    }

}
