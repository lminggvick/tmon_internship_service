package com.tmoncorp.wettyapi.service.factory;

import com.tmoncorp.wettyapi.model.CircleGraph;
import com.tmoncorp.wettyapi.model.type.GraphTypeGroup;
import com.tmoncorp.wettyapi.repository.GraphRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CircleGraphCreator implements GraphModelCreator<CircleGraph> {

    @Autowired
    private GraphRepository graphRepository;

    @Override
    public List<CircleGraph> createGraphDataList(int apiTypeId) {
        return graphRepository.getGraphDataFromCircleGraph(apiTypeId);
    }

    @Override
    public boolean isGraphType(GraphTypeGroup graphTypeGroup) {
        return GraphTypeGroup.CIRCLE_GRAPH.equals(graphTypeGroup);
    }
}
