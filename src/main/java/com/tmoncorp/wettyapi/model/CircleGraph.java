package com.tmoncorp.wettyapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CircleGraph extends GraphModel {
    private String pieName;
    private int count;

    public CircleGraph(int graphModelId, int graphId, LocalDateTime createDate, String pieName, int count) {
        super(graphModelId, graphId, createDate);
        this.pieName = pieName;
        this.count = count;
    }

    @Override
    public String getBase() {
        return this.pieName;
    }

    @Override
    public int getData() {
        return this.count;
    }

    @Override
    public void setBase(String base) {
        this.pieName = base;
    }

    @Override
    public void setData(int data) {
        this.count = data;
    }
}
