package com.tmoncorp.wettyapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tmoncorp.core.model.BaseModelSupport;
import lombok.*;

import java.time.LocalDateTime;

/**
 * XyGraph와 CircleGraph를 추상화한 상위 클래스
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class GraphModel extends BaseModelSupport {
    @JsonIgnore
    protected int graphDataId;
    @JsonIgnore
    protected int apiTypeId;

    protected LocalDateTime createDate;

    @JsonIgnore
    public abstract String getBase();
    @JsonIgnore
    public abstract int getData();
    public abstract void setBase(String base);
    public abstract void setData(int data);

}
