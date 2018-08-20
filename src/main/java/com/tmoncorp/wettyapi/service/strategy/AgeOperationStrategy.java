package com.tmoncorp.wettyapi.service.strategy;

import com.tmoncorp.wettyapi.model.GraphModel;
import com.tmoncorp.wettyapi.model.type.BaseTypeGroup;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Component
public class AgeOperationStrategy implements GraphOperationStrategy {

    @Override
    public <G extends GraphModel> List<G> operateByBaseTypeCategory(List<G> originGraphDataList, Map<String, String> categoryMap) {
        List<G> list = GraphOperationStrategy.super.operateByBaseTypeCategory(originGraphDataList, categoryMap);
        list.sort(new AgeComparator());
        return list;
    }

    @Override
    public boolean isBaseType(BaseTypeGroup baseTypeGroup) {
        return BaseTypeGroup.AGE.equals(baseTypeGroup);
    }

    private static class AgeComparator implements Comparator<GraphModel> {

        @Override
        public int compare(GraphModel o1, GraphModel o2) {
            int age1 = convertStringToIntAge(o1.getBase());
            int age2 = convertStringToIntAge(o2.getBase());
            return age1 - age2;
        }

        /**
         * @param base "10대", "20대", "30대", "40대", "50대"
         * @return 10, 20, 30, 40, 50
         */
        private static int convertStringToIntAge(String base) {
            return Integer.parseInt(base.substring(0, base.length() - 1));
        }

    }

}
