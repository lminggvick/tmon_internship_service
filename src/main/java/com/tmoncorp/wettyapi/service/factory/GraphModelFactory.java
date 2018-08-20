package com.tmoncorp.wettyapi.service.factory;

import com.tmoncorp.wettyapi.exception.GraphCreationException;
import com.tmoncorp.wettyapi.model.GraphModel;
import com.tmoncorp.wettyapi.model.type.GraphTypeGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GraphModelFactory {

    @Autowired
    private List<GraphModelCreator<? extends GraphModel>> graphModelCreatorList;

    @SuppressWarnings("unchecked")
    public <G extends GraphModel> GraphModelCreator<G> getGraphModelCreator(GraphTypeGroup graphType) {
        for (GraphModelCreator creator : graphModelCreatorList) {
            if (creator.isGraphType(graphType)) {
                return creator;
            }
        }
        throw new GraphCreationException();
    }

}
