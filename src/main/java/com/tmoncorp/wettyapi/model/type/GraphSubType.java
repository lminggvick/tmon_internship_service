package com.tmoncorp.wettyapi.model.type;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GraphSubType implements EnumMapperType {
    BAR_GRAPH("막대 그래프"),
    LINEAR_GRAPH("선형 그래프"),
    PIE_GRAPH("파이 그래프"),

    UNKNOWN("Mismatch GraphSubType");

    private String title;

    GraphSubType(String title) {
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
