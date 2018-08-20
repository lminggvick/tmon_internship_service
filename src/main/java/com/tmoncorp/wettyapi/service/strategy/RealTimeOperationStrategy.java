package com.tmoncorp.wettyapi.service.strategy;

import com.tmoncorp.wettyapi.exception.GraphCreationException;
import com.tmoncorp.wettyapi.model.GraphModel;
import com.tmoncorp.wettyapi.model.type.BaseSubType;
import com.tmoncorp.wettyapi.model.type.BaseTypeGroup;
import com.tmoncorp.wettyapi.utils.LocalDateTimeConstants;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RealTimeOperationStrategy extends DateOperationStrategy implements GraphOperationStrategy {

    /**
     * BaseTypeGroup이 REAL_TIME일때,
     * ONE_MONTH(30일), ONE_WEEK(7일), ONE_DAY(1일) 의 데이터를 조회
     *
     * @param originGraphDataList
     * @param categoryMap
     * @param <G>
     * @return
     */
    @Override
    public <G extends GraphModel> List<G> operateByBaseTypeCategory(List<G> originGraphDataList, Map<String, String> categoryMap) {
        long days = getDays(categoryMap);

        String end_date = LocalDateTime.now().format(LocalDateTimeConstants.DATE_TIME_HOUR_FORMATTER);
        LocalDateTime start_date = LocalDateTime.parse(end_date, LocalDateTimeConstants.DATE_TIME_HOUR_FORMATTER).minusDays(days);

        Map<String, String> tempCategoryMap = new HashMap<>();
        tempCategoryMap.put(BaseSubType.START_DATE.getCode(), start_date.format(LocalDateTimeConstants.DATE_TIME_HOUR_FORMATTER));
        tempCategoryMap.put(BaseSubType.END_DATE.getCode(), end_date);
        tempCategoryMap.put(BaseSubType.SPLIT_TIME.getCode(), "21600"); //6시간

        return super.operateByBaseTypeCategory(originGraphDataList, tempCategoryMap);
    }

    @Override
    public boolean isBaseType(BaseTypeGroup baseTypeGroup) {
        return BaseTypeGroup.REAL_TIME.equals(baseTypeGroup);
    }

    private long getDays(Map<String, String> categoryMap) {
        for (String key : categoryMap.keySet()) {
            if (Boolean.parseBoolean(categoryMap.get(key))) {
                return Long.parseLong(BaseSubType.valueOf(key).getTitle());
            }
        }
        throw new GraphCreationException();
    }

}

