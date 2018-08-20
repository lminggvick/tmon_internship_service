package com.tmoncorp.wettyapi.exception;

import com.tmoncorp.core.exception.ApiException;
import com.tmoncorp.wettyapi.utils.WettyExceptionConstants;

public class NotFoundException extends ApiException {

    public NotFoundException(String message) {
        super(WettyExceptionConstants.NOT_FOUND.getMessage() + "(" + message + ")",
                WettyExceptionConstants.NOT_FOUND.getCode()
        );
    }

}
