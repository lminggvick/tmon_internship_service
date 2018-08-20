package com.tmoncorp.wettyapi.model.type;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DataType implements EnumMapperType {
    SOLD_COUNT("판매수"),
    VISITED_COUNT("방문수"),
    INQUIRY_COUNT("문의수"),
    DEAL_PRICE("판매가격"),

    UNKNOWN("Mismatch DataType");

    private String title;

    DataType(String title) {
        this.title = title;
    }

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "{" +
                "code='" + name() + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
