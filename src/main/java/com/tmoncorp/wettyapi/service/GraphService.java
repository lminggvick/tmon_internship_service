package com.tmoncorp.wettyapi.service;

import com.tmoncorp.wettyapi.exception.NotFoundException;
import com.tmoncorp.wettyapi.service.factory.GraphModelCreator;
import com.tmoncorp.wettyapi.service.factory.GraphModelFactory;
import com.tmoncorp.wettyapi.model.DashboardGraphCollection;
import com.tmoncorp.wettyapi.model.Graph;
import com.tmoncorp.wettyapi.model.GraphModel;
import com.tmoncorp.wettyapi.model.type.BaseTypeCategory;
import com.tmoncorp.wettyapi.model.type.GraphTypeGroup;
import com.tmoncorp.wettyapi.repository.DashboardRepository;
import com.tmoncorp.wettyapi.repository.GraphRepository;
import com.tmoncorp.wettyapi.service.strategy.GraphOperationStrategy;
import com.tmoncorp.wettyapi.service.strategy.GraphOperatorFactory;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GraphService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DashboardRepository dashboardRepository;
    @Autowired
    private GraphRepository graphRepository;
    @Autowired
    private GraphModelFactory graphModelFactory;
    @Autowired
    private GraphOperatorFactory graphOperatorFactory;

    /**
     * Graph 정보와 Graph를 그리기 위한 데이터 리스트를 반환
     *
     * @param dashboardNo
     * @param graphId
     * @param <G>
     * @return
     */
    public <G extends GraphModel> Graph<G> getGraphData(int dashboardNo, int graphId) {
        Graph<G> graph = graphRepository.getGraph(graphId);
        if (graph == null) {
            throw new NotFoundException("Not exist Graph");
        }

        DashboardGraphCollection graphCollection = dashboardRepository.getDashboardGraph(new DashboardGraphCollection(dashboardNo, graphId));
        if (graphCollection == null) {
            logger.error("No. " + dashboardNo + "-대시보드에 " + graphId + "-그래프가 없습니다.");
            throw new NotFoundException("No connection - dashboard and graph");
        }
        graph.setGraphType(GraphTypeGroup.create(graphCollection.getGraphSubType().getCode()));

        int apiTypeId = graphRepository.getApiTypeIdByGraphId(graphId);
        GraphModelCreator<G> graphModelCreator = graphModelFactory.getGraphModelCreator(graph.getGraphType());
        List<G> list = graphModelCreator.createGraphDataList(apiTypeId);
        if (CollectionUtils.isEmpty(list)) {
            graph.setGraphDataList(Collections.EMPTY_LIST);
            return graph;
        }
        List<G> groupList = groupByBaseType(list);

        List<BaseTypeCategory> baseTypeCategories = graphRepository.getBaseTypeCategories(graphId);
        GraphOperationStrategy graphOperationStrategy = graphOperatorFactory.createGraphOperator(graph.getBaseType());
        graph.setGraphDataList(graphOperationStrategy.operateByBaseTypeCategory(groupList, convertBaseTypeCategoryFromListToMap(baseTypeCategories)));

        return graph;
    }

    /**
     * List를 Map으로 변환
     * BaseTypeCategory (id, categoryKey, categoryValue)
     * @param baseTypeCategories
     * @return Map<categoryKey, categoryValue>
     */
    private Map<String, String> convertBaseTypeCategoryFromListToMap(List<BaseTypeCategory> baseTypeCategories) {
        return baseTypeCategories.stream().collect(Collectors.toMap(BaseTypeCategory::getCategoryKey, BaseTypeCategory::getCategoryValue));
    }

    /**
     * BaseType이 동일한 row에 대해서 Data를 합치는 작업
     * @param graphList
     * @param <G>
     * @return List<G>
     */
    private <G extends GraphModel> List<G> groupByBaseType(List<G> graphList) {
        Map<String, List<G>> baseGroupMap = graphList.stream().collect(Collectors.groupingBy(graph -> graph.getBase()));

        List<G> groupingList = new ArrayList<>();
        for (List<G> list : baseGroupMap.values()) {
            int totalData = list.stream().mapToInt(g -> g.getData()).sum();
            G tempGraph = list.get(0);
            tempGraph.setData(totalData);
            groupingList.add(tempGraph);
        }
        return groupingList;
    }

}
