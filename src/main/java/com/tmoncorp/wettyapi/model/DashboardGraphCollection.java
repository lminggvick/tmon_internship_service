package com.tmoncorp.wettyapi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmoncorp.core.model.BaseModelSupport;
import com.tmoncorp.wettyapi.model.type.GraphSubType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardGraphCollection extends BaseModelSupport {
    private long collectionId;
    private int dashboardId;
    private long graphId;
    private GraphSubType graphSubType;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String graphName;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private LocalDateTime updateDate;

    public DashboardGraphCollection(int dashboardId, long graphId) {
        this.dashboardId = dashboardId;
        this.graphId = graphId;
    }

    public DashboardGraphCollection(long collectionId, int dashboardId, long graphId, GraphSubType graphSubType) {
        this.collectionId = collectionId;
        this.dashboardId = dashboardId;
        this.graphId = graphId;
        this.graphSubType = graphSubType;
    }
}
