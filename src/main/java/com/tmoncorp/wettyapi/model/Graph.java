package com.tmoncorp.wettyapi.model;

import com.tmoncorp.core.model.BaseModelSupport;
import com.tmoncorp.wettyapi.model.type.BaseTypeGroup;
import com.tmoncorp.wettyapi.model.type.DataType;
import com.tmoncorp.wettyapi.model.type.GraphTypeGroup;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Graph<G extends GraphModel> extends BaseModelSupport {
    private int graphId;
    private String graphName;
    private String graphDescription;
    private GraphTypeGroup graphType;
    private BaseTypeGroup baseType;
    private DataType dataType;
    private int graphUpdateCycle;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private List<G> graphDataList;      //XyGraph or CircleGraph

    public List<G> getGraphDataList() {
        return graphDataList;
    }

    public void setGraphDataList(List<G> graphDataList) {
        this.graphDataList = graphDataList;
    }
}
