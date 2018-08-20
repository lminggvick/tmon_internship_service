package com.tmoncorp.wettyapi.exception;

import com.tmoncorp.core.exception.ApiException;
import com.tmoncorp.wettyapi.utils.WettyExceptionConstants;

public class GraphCreationException extends ApiException {

    public GraphCreationException() {
        super(WettyExceptionConstants.GRAPH_CREATE_FAIL.getMessage(),
                WettyExceptionConstants.GRAPH_CREATE_FAIL.getCode()
        );
    }

}
