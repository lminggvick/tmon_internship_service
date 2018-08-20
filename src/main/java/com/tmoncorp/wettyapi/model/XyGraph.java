package com.tmoncorp.wettyapi.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class XyGraph extends GraphModel {
    private String dataX;
    private int dataY;

    public XyGraph(int graphModelId, int graphId, LocalDateTime createDate, String dataX, int dataY) {
        super(graphModelId, graphId, createDate);
        this.dataX = dataX;
        this.dataY = dataY;
    }

    @Override
    public String getBase() {
        return this.dataX;
    }

    @Override
    public int getData() {
        return this.dataY;
    }

    @Override
    public void setBase(String base) {
        this.dataX = base;
    }

    @Override
    public void setData(int data) {
        this.dataY = data;
    }
}
