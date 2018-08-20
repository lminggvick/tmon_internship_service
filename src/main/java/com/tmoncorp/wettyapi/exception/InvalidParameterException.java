package com.tmoncorp.wettyapi.exception;

import com.tmoncorp.core.exception.ApiException;
import com.tmoncorp.wettyapi.utils.WettyExceptionConstants;

/**
 * 400의 의미와 다르다.
 * 나머지 Exception도 GraphNotFoundException처럼 정의해주어서 통일시킬 것 -> 그럼 프론트도 편해짐
 */
public class InvalidParameterException extends ApiException {

    public InvalidParameterException(String message) {
        super(WettyExceptionConstants.INVALID_PARAM.getMessage() + "(" + message + ")",
                WettyExceptionConstants.INVALID_PARAM.getCode()
        );
    }

}
