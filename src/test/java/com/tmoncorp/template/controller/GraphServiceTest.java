package com.tmoncorp.template.controller;

import com.tmoncorp.wettyapi.model.XyGraph;
import com.tmoncorp.wettyapi.model.type.BaseSubType;
import com.tmoncorp.wettyapi.model.type.BaseTypeCategory;
import com.tmoncorp.wettyapi.model.type.GraphTypeGroup;
import com.tmoncorp.wettyapi.service.strategy.DateOperationStrategy;
import com.tmoncorp.wettyapi.service.strategy.GraphOperationStrategy;
import com.tmoncorp.wettyapi.utils.LocalDateTimeConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/mvc-config.xml", "classpath:/spring/application*"})
@WebAppConfiguration
public class GraphServiceTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void reflectionTest() throws Exception {
        Class classTest = Class.forName("com.tmoncorp.wettyapi.model.Graph");
        Field[] fields = classTest.getDeclaredFields();
        Stream.of(fields).forEach(field -> logger.info(String.valueOf(field)));
    }

    @Test
    public void testDateOperationStrategy() {
        GraphOperationStrategy operationStrategy = new DateOperationStrategy();
        List<XyGraph> xyGraphList = Arrays.asList(
                new XyGraph(1, 1, LocalDateTime.now(), "2018-07-27 10:00:00 ~ 2018-07-27 11:00:00", 100),
                new XyGraph(2, 1, LocalDateTime.now(), "2018-07-27 15:00:00 ~ 2018-07-27 16:00:00", 300),
                new XyGraph(3, 1, LocalDateTime.now(), "2018-07-27 12:00:00 ~ 2018-07-27 13:00:00", 200),
                new XyGraph(4, 1, LocalDateTime.now(), "2018-07-27 11:00:00 ~ 2018-07-27 12:00:00", 123),
                new XyGraph(5, 1, LocalDateTime.now(), "2018-07-27 14:00:00 ~ 2018-07-27 15:00:00", 567),
                new XyGraph(6, 1, LocalDateTime.now(), "2018-07-27 13:00:00 ~ 2018-07-27 14:00:00", 123),
                new XyGraph(7, 1, LocalDateTime.now(), "2018-07-27 16:00:00 ~ 2018-07-27 17:00:00", 341),
                new XyGraph(8, 1, LocalDateTime.now(), "2018-07-27 17:00:00 ~ 2018-07-27 18:00:00", 345),
                new XyGraph(9, 1, LocalDateTime.now(), "2018-07-27 18:00:00 ~ 2018-07-27 19:00:00", 678),
                new XyGraph(10, 1, LocalDateTime.now(), "2018-07-27 19:00:00 ~ 2018-07-27 20:00:00", 812)
        );
        List<BaseTypeCategory> baseTypeCategories = Arrays.asList(
                new BaseTypeCategory(1, BaseSubType.START_DATE.name(), "2018-07-27 12:00:00"),
                new BaseTypeCategory(1, BaseSubType.END_DATE.name(), "2018-07-27 20:00:00"),
                new BaseTypeCategory(1, BaseSubType.SPLIT_TIME.name(), "7200")
        );

        logger.info(String.valueOf(operationStrategy.operateByBaseTypeCategory(xyGraphList, convertBaseTypeCategoryFromListToMap(baseTypeCategories))));
    }

    @Test
    public void testDefaultOperationStrategy() {
        String end_date = LocalDateTime.now().format(LocalDateTimeConstants.DATE_TIME_HOUR_FORMATTER);
        logger.info("END_DATE :: " + end_date);
        LocalDateTime start_date = LocalDateTime.parse(end_date, LocalDateTimeConstants.DATE_TIME_HOUR_FORMATTER).minusDays(5);
        logger.info("START_DATE :: " + start_date.format(LocalDateTimeConstants.DATE_TIME_HOUR_FORMATTER));
//        GraphOperationStrategy operationStrategy = GraphOperatorFactory.createGraphOperator(BaseTypeGroup.AGE);
//        List<CircleGraph> circleGraphs = Arrays.asList(
//                new CircleGraph(1, 1, LocalDateTime.now(), BaseSubType.AGE_10.name(), 100),
//                new CircleGraph(2, 1, LocalDateTime.now(), BaseSubType.AGE_20.name(), 300),
//                new CircleGraph(3, 1, LocalDateTime.now(), BaseSubType.AGE_30.name(), 200),
//                new CircleGraph(4, 1, LocalDateTime.now(), BaseSubType.AGE_40.name(), 123),
//                new CircleGraph(5, 1, LocalDateTime.now(), BaseSubType.AGE_50.name(), 567)
//        );
//
//        List<BaseTypeCategory> baseTypeCategories = Arrays.asList(
//                new BaseTypeCategory(1, BaseTypeGroup.AGE.name(), BaseSubType.AGE_10.name()),
//                new BaseTypeCategory(1, BaseTypeGroup.AGE.name(), BaseSubType.AGE_20.name()),
//                new BaseTypeCategory(1, BaseTypeGroup.AGE.name(), BaseSubType.AGE_30.name()),
//                new BaseTypeCategory(1, BaseTypeGroup.AGE.name(), BaseSubType.AGE_40.name())
//        );

//        List<BaseTypeCategory> baseTypeCategories = Arrays.asList(
//                new BaseTypeCategory(1, BaseSubType.AGE_10.name(), "true"),
//                new BaseTypeCategory(1, BaseSubType.AGE_20.name(), "true"),
//                new BaseTypeCategory(1, BaseSubType.AGE_30.name(), "true"),
//                new BaseTypeCategory(1, BaseSubType.AGE_40.name(), "true"),
//                new BaseTypeCategory(1, BaseSubType.AGE_50.name(), "false")
//        );
//
//        logger.info(String.valueOf(operationStrategy.operateByBaseTypeCategory(circleGraphs, convertBaseTypeCategoryFromListToMap(baseTypeCategories))));
    }

    private Map<String, String> convertBaseTypeCategoryFromListToMap(List<BaseTypeCategory> baseTypeCategories) {
        return baseTypeCategories.stream().collect(Collectors.toMap(BaseTypeCategory::getCategoryKey, BaseTypeCategory::getCategoryValue));
    }

    @Test
    public void testOperateLocalDateTime() {
        LocalDateTime localDateTime1 = LocalDateTime.now();
        LocalDateTime localDateTime2 = LocalDateTime.parse("2018-07-27 10:00:00", LocalDateTimeConstants.DATE_TIME_FORMATTER);
        LocalDateTime localDateTime3 = LocalDateTime.parse("2018-07-27 10:00:00", LocalDateTimeConstants.DATE_TIME_FORMATTER);

        logger.info("LocalDateTime Test : " + localDateTime1.isBefore(localDateTime2));
        logger.info("LocalDateTime Test : " + localDateTime2.isBefore(localDateTime1));
        logger.info("LocalDateTime Test : " + localDateTime2.isEqual(localDateTime3));
    }

    @Test
    public void testEnumValues() {
        logger.debug("SUB TYPE :: " + GraphTypeGroup.create("LINEAR_GRAPH"));
        logger.debug("SUB TYPE :: " + GraphTypeGroup.create("BAR_GRAPH"));
        logger.debug("SUB TYPE :: " + GraphTypeGroup.create("PIE_GRAPH"));
    }

}
