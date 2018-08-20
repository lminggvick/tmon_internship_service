package com.tmoncorp.wettyapi.exception;

import com.tmoncorp.core.exception.ApiException;
import com.tmoncorp.wettyapi.utils.WettyExceptionConstants;

public class ObjectCreationWarningException extends ApiException {

    public ObjectCreationWarningException() {
        super(WettyExceptionConstants.OBJECT_CREATE_FAIL.getMessage(),
                WettyExceptionConstants.OBJECT_CREATE_FAIL.getCode()
        );
    }

}
