package com.tmoncorp.wettyapi.service.strategy;

import com.tmoncorp.wettyapi.exception.GraphCreationException;
import com.tmoncorp.wettyapi.model.DatePeriod;
import com.tmoncorp.wettyapi.model.GraphModel;
import com.tmoncorp.wettyapi.model.XyGraph;
import com.tmoncorp.wettyapi.model.type.BaseSubType;
import com.tmoncorp.wettyapi.model.type.BaseTypeGroup;
import com.tmoncorp.wettyapi.utils.LocalDateTimeConstants;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * BaseType이 DATE인 경우 StartDate, EndDate, SplitTime에 따라 연산 작업이 필요함
 */
@Component
public class DateOperationStrategy implements GraphOperationStrategy {

    @Override
    public <G extends GraphModel> List<G> operateByBaseTypeCategory(List<G> originGraphDataList, Map<String, String> categoryMap) {

        LocalDateTime settingStartDate = LocalDateTime.parse(categoryMap.get(BaseSubType.START_DATE.name()), LocalDateTimeConstants.DATE_TIME_FORMATTER);
        LocalDateTime settingEndDate = LocalDateTime.parse(categoryMap.get(BaseSubType.END_DATE.name()), LocalDateTimeConstants.DATE_TIME_FORMATTER);

        if (settingStartDate.isAfter(settingEndDate)) {
            throw new GraphCreationException();
        }

        long settingSplitTime = Long.parseLong(categoryMap.get(BaseSubType.SPLIT_TIME.name()));
        if (settingSplitTime < 3600) {
            settingSplitTime = 3600;
        }

        List<G> graphModelList = originGraphDataList.parallelStream()
                .filter(new GraphDateRangeFilter(settingStartDate, settingEndDate))
                .collect(Collectors.toList());

        Map<String, Integer> graphDateGroupMap = mergeGraphDateBySplitTime(graphModelList, new DatePeriod(settingStartDate, settingEndDate), settingSplitTime);

        List<G> resultGraphDataList = new ArrayList<>();
        for (String key : graphDateGroupMap.keySet()) {
            resultGraphDataList.add((G) new XyGraph(key, graphDateGroupMap.get(key)));
        }
        resultGraphDataList.sort((p, o) -> parseStartDate(p.getBase()).compareTo(parseStartDate(o.getBase())));
        return resultGraphDataList;
    }

    @Override
    public boolean isBaseType(BaseTypeGroup baseTypeGroup) {
        return BaseTypeGroup.DATE.equals(baseTypeGroup);
    }

    /**
     * SPLIT_TIME 단위로 데이터 병합하기 (현재 시간으로부터 SPLIT_TIME 계산)
     *
     * @param graphModelList
     * @param <G>
     * @return
     */
    private <G extends GraphModel> Map<String, Integer> mergeGraphDateBySplitTime(List<G> graphModelList, DatePeriod settingDate, long settingSplitTime) {
        Map<String, Integer> graphDateGroupMap = new HashMap<>();
        while (settingDate.getEndDate().isAfter(settingDate.getStartDate())) {
            LocalDateTime endDate = settingDate.getStartDate().plus(settingSplitTime, ChronoUnit.SECONDS);

            String dateKey = settingDate.getStartDate().format(LocalDateTimeConstants.DATE_TIME_FORMATTER)
                    + " ~ " + endDate.format(LocalDateTimeConstants.DATE_TIME_FORMATTER);

            settingDate.setStartDate(endDate);
            graphDateGroupMap.put(dateKey, 0);
        }

        for (GraphModel graph : graphModelList) {
            String[] graphDate = graph.getBase().split(" ~ ");  //yyyy-MM-dd hh:mm:ss ~ yyyy-MM-dd hh:mm:ss
            DatePeriod graphDatePeriod = new DatePeriod(
                    LocalDateTime.parse(graphDate[0], LocalDateTimeConstants.DATE_TIME_FORMATTER),
                    LocalDateTime.parse(graphDate[1], LocalDateTimeConstants.DATE_TIME_FORMATTER)
            );

            for (String baseKey : graphDateGroupMap.keySet()) {
                String[] baseKeyDate = baseKey.split(" ~ ");
                DatePeriod standardDate = new DatePeriod(
                        LocalDateTime.parse(baseKeyDate[0], LocalDateTimeConstants.DATE_TIME_FORMATTER),
                        LocalDateTime.parse(baseKeyDate[1], LocalDateTimeConstants.DATE_TIME_FORMATTER)
                );

                if (isInsideRange(graphDatePeriod, standardDate)) {
                    graphDateGroupMap.put(baseKey, graphDateGroupMap.get(baseKey) + graph.getData());
                }
            }
        }

        return graphDateGroupMap;
    }

    private LocalDateTime parseStartDate(String baseDate) {
        String startDate = baseDate.split(" ~ ")[0];
        return LocalDateTime.parse(startDate, LocalDateTimeConstants.DATE_TIME_FORMATTER);
    }

    /**
     * graphDate가 standardDate 안에 포함되는지 아닌지 확인하는 기능
     *
     * @param graphDate    추가할 시간
     * @param standardDate 기준이 되는 날짜 + 시간 범위
     * @return true : 포함, false : 미포함
     */
    private boolean isInsideRange(DatePeriod graphDate, DatePeriod standardDate) {
        if (standardDate.getStartDate().isEqual(graphDate.getStartDate())) {
            return true;
        }

        if (standardDate.getStartDate().isAfter(graphDate.getStartDate())
                && standardDate.getEndDate().isBefore(graphDate.getEndDate())) {
            return true;
        }

        if (standardDate.getEndDate().isEqual(graphDate.getEndDate())) {
            return true;
        }

        return false;
    }

    private class GraphDateRangeFilter implements Predicate<GraphModel> {
        private LocalDateTime startDate;
        private LocalDateTime endDate;

        GraphDateRangeFilter(LocalDateTime startDate, LocalDateTime endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
        }

        @Override
        public boolean test(GraphModel graphModel) {
            String[] graphDate = graphModel.getBase().split(" ~ ");  //yyyy-MM-dd hh:mm:ss ~ yyyy-MM-dd hh:mm:ss
            LocalDateTime graphStartDate = LocalDateTime.parse(graphDate[0], LocalDateTimeConstants.DATE_TIME_FORMATTER);
            LocalDateTime graphEndDate = LocalDateTime.parse(graphDate[1], LocalDateTimeConstants.DATE_TIME_FORMATTER);

            if (startDate.isAfter(graphStartDate)) {
                return false;
            }
            if (endDate.isBefore(graphEndDate)) {
                return false;
            }
            return true;
        }
    }

}
