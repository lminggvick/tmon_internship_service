package com.tmoncorp.wettyapi.service.strategy;

import com.tmoncorp.wettyapi.model.GraphModel;
import com.tmoncorp.wettyapi.model.type.BaseSubType;
import com.tmoncorp.wettyapi.model.type.BaseTypeGroup;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface GraphOperationStrategy {

    default <G extends GraphModel> List<G> operateByBaseTypeCategory(List<G> originGraphDataList, Map<String, String> categoryMap) {
        List<G> graphList = originGraphDataList.stream()
                .filter(graph -> Boolean.parseBoolean(categoryMap.get(graph.getBase())))
                .collect(Collectors.toList());
        for (G g : graphList) {
            g.setBase(BaseSubType.valueOf(g.getBase()).getTitle());
        }
        return graphList;
    }

    boolean isBaseType(BaseTypeGroup baseTypeGroup);

}
