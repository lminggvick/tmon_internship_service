package com.tmoncorp.wettyapi.service.strategy;

import com.tmoncorp.wettyapi.exception.GraphCreationException;
import com.tmoncorp.wettyapi.model.type.BaseTypeGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GraphOperatorFactory {

    @Autowired
    private List<GraphOperationStrategy> graphOperationStrategyList;

    public GraphOperationStrategy createGraphOperator(BaseTypeGroup baseTypeGroup) {
        for (GraphOperationStrategy strategy : graphOperationStrategyList) {
            if (strategy.isBaseType(baseTypeGroup)) {
                return strategy;
            }
        }
        throw new GraphCreationException();
    }

}
