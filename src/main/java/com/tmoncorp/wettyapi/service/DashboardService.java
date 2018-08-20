package com.tmoncorp.wettyapi.service;

import com.tmoncorp.wettyapi.exception.NotFoundException;
import com.tmoncorp.wettyapi.model.Dashboard;
import com.tmoncorp.wettyapi.model.DashboardGraphCollection;
import com.tmoncorp.wettyapi.repository.DashboardRepository;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The type Dashboard wettyapi.
 */
@Service
public class DashboardService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DashboardRepository dashboardRepository;

    /**
     * 전체 대시보드 조회
     *
     * @return the dashboard list
     */
    public List<Dashboard> getDashboardList() {
        List<Dashboard> dashboardList = dashboardRepository.getDashboardList();
        if (CollectionUtils.isEmpty(dashboardList)) {
            return Collections.emptyList();
        }

        List<DashboardGraphCollection> dashboardGraphCollections = dashboardRepository.getDashboardGraphCollections();
        groupByDashboardId(dashboardList, dashboardGraphCollections);
        return dashboardList;
    }

    /**
     * dashboard의 정보 조회 - 대시보드에 등록된 그래프 리스트에서 실제 데이터를 제외한 GRAPH_ID, GRAPH_TYPE 을 제공
     *
     * @param dashboardNo the dashboard no
     * @return the dashboard summary information
     */
    public Dashboard getDashboardSummaryInformation(int dashboardNo, int page, int max) {
        Dashboard dashboard = dashboardRepository.getDashboardSummaryInformation(dashboardNo);
        if (Objects.isNull(dashboard)) {
            throw new NotFoundException("Not exist Dashboard - No. " + dashboardNo);
        }
        List<DashboardGraphCollection> collectionList = dashboard.getGraphCollectionList();
        collectionList.sort((g1, g2) -> (int) g2.getGraphId() - (int) g1.getGraphId());

        dashboard.setTotalSize(collectionList.size());
        dashboard.setGraphCollectionList(subListInPage(collectionList, page, max));
        return dashboard;
    }

    /**
     * 각 대시보드에 포함되어 있는 그래프 매핑
     *
     * @param dashboardList
     * @param dashboardGraphCollections
     */
    private void groupByDashboardId(List<Dashboard> dashboardList, List<DashboardGraphCollection> dashboardGraphCollections) {
        Map<Integer, List<DashboardGraphCollection>> graphCollectionMap
                = dashboardGraphCollections.stream().collect(Collectors.groupingBy(graph -> graph.getDashboardId()));
        for (Dashboard dashboard : dashboardList) {
            dashboard.setGraphCollectionList(graphCollectionMap.get(dashboard.getDashboardId()));
        }
    }

    /**
     * Graph가 존재하지 않는 Dashboard 제외, max 개 만큼 반환
     *
     * @param dashboardList
     * @return
     */
    private List<Dashboard> filterDashboard(List<Dashboard> dashboardList) {
        return dashboardList.stream()
                .filter(dashboard -> CollectionUtils.isNotEmpty(dashboard.getGraphCollectionList()))
                .sorted((d1, d2) -> d2.getDashboardId() - d1.getDashboardId())
                .collect(Collectors.toList());

//        return subListInPage(dashboardList, page, max);
    }

    private <T> List<T> subListInPage(List<T> list, int page, int max) {
        int fromIndex = (page - 1) * max;
        int toIndex = page * max;
        int size = list.size();

        if (size < toIndex) {
            toIndex = size - fromIndex;
        }
        return list.subList(fromIndex, toIndex);
    }

}
