package com.tmoncorp.wettyapi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmoncorp.core.model.BaseModelSupport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Dashboard extends BaseModelSupport {
    private int dashboardId;
    private String dashboardName;
    private String dashboardDescription;
    private int apiId;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer totalSize;
    private List<DashboardGraphCollection> graphCollectionList;

    public Dashboard(String dashboardName, String dashboardDescription, int apiId) {
        this.dashboardName = dashboardName;
        this.dashboardDescription = dashboardDescription;
        this.apiId = apiId;
    }
}
