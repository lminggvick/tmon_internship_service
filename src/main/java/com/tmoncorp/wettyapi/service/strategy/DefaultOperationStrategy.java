package com.tmoncorp.wettyapi.service.strategy;

import com.tmoncorp.wettyapi.model.GraphModel;
import com.tmoncorp.wettyapi.model.type.BaseTypeGroup;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Default는 interface에서 디폴트 메소드로 구현해도 좋을 것
 */
@Component
public class DefaultOperationStrategy implements GraphOperationStrategy {

    @Override
    public <G extends GraphModel> List<G> operateByBaseTypeCategory(List<G> originGraphDataList, Map<String, String> categoryMap) {
        return GraphOperationStrategy.super.operateByBaseTypeCategory(originGraphDataList, categoryMap);
    }

    @Override
    public boolean isBaseType(BaseTypeGroup baseTypeGroup) {
        if (BaseTypeGroup.GENDER.equals(baseTypeGroup)) {
            return true;
        }
        if (BaseTypeGroup.REGION.equals(baseTypeGroup)) {
            return true;
        }
        return false;
    }

}
