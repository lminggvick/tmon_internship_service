package com.tmoncorp.wettyapi.service.factory;

import com.tmoncorp.wettyapi.model.XyGraph;
import com.tmoncorp.wettyapi.model.type.GraphTypeGroup;
import com.tmoncorp.wettyapi.repository.GraphRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class XyGraphCreator implements GraphModelCreator<XyGraph> {

    @Autowired
    private GraphRepository graphRepository;

    @Override
    public List<XyGraph> createGraphDataList(int apiTypeId) {
        return graphRepository.getGraphDataFromXyGraph(apiTypeId);
    }

    @Override
    public boolean isGraphType(GraphTypeGroup graphTypeGroup) {
        return GraphTypeGroup.XY_GRAPH.equals(graphTypeGroup);
    }
}
