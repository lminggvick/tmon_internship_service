package com.tmoncorp.wettyapi.utils;

public enum WettyExceptionConstants {

    NOT_FOUND("100404", "해당하는 정보를 찾지 못했습니다."),
    GRAPH_CREATE_FAIL("100500", "정의되지 않은 Graph Type 입니다. (그래프를 새로 생성하세요)"),
    INVALID_PARAM("100400", "파라미터가 유효하지 않은 데이터입니다."),
    OBJECT_CREATE_FAIL("100501", "[서버 LOG] 상수 클래스는 인스턴스 생성하지말자");

    private String code;
    private String message;

    WettyExceptionConstants(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
