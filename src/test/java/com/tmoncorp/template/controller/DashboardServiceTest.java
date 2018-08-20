package com.tmoncorp.template.controller;

import com.tmoncorp.wettyapi.model.*;
import com.tmoncorp.wettyapi.model.type.BaseSubType;
import com.tmoncorp.wettyapi.model.type.BaseTypeCategory;
import com.tmoncorp.wettyapi.repository.DashboardRepository;
import com.tmoncorp.wettyapi.service.DashboardService;
import com.tmoncorp.wettyapi.service.GraphService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/mvc-config.xml", "classpath:/spring/application*" })
@WebAppConfiguration
public class DashboardServiceTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Mock
    private DashboardRepository dashboardRepository;

    @InjectMocks
    private DashboardService dashboardService;

    @InjectMocks
    private GraphService graphService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

/*    @Test
    public void getDashboardList() {
        List<Dashboard> mockDashboardList = new ArrayList<>();
        mockDashboardList.add(new Dashboard("11", "asdasd", 1));
        mockDashboardList.add(new Dashboard("22", "zxcvzxvc", 2));
        given(dashboardRepository.getDashboardList()).willReturn(mockDashboardList);

        List<DashboardGraphCollection> mockDashboardGraphCollections = new ArrayList<>();
//        mockDashboardGraphCollections.add(new DashboardGraphCollection(1, 1, 1, GraphSubType.BAR_GRAPH, LocalDateTime.now()));
//        mockDashboardGraphCollections.add(new DashboardGraphCollection(2, 1, 2, GraphSubType.BAR_GRAPH, LocalDateTime.now().minusHours(3)));
        given(dashboardRepository.getDashboardGraphCollections()).willReturn(mockDashboardGraphCollections);

        final List<Dashboard> dashboardListTest = dashboardService.getDashboardList();

        verify(dashboardRepository).getDashboardList();
        verify(dashboardRepository).getDashboardGraphCollections();
        assertThat("getDashboardList Test", dashboardListTest, is(sameInstance(mockDashboardList)));
        logger.info(String.valueOf(dashboardListTest));
    }*/

    @Test
    public void operateGraphWithDate() {
        List<GraphModel> graphModelList = new ArrayList<>();
        graphModelList.add(new XyGraph(1, 1, LocalDateTime.now(), "2018-07-26 17:00:00 ~ 2018-07-26 17:52:00", 10));
        graphModelList.add(new XyGraph(1, 1, LocalDateTime.now(), "2018-07-26 16:00:00 ~ 2018-07-26 17:00:00", 190));
        graphModelList.add(new XyGraph(1, 1, LocalDateTime.now(), "2018-07-26 15:00:00 ~ 2018-07-26 16:00:00", 210));
        graphModelList.add(new XyGraph(1, 1, LocalDateTime.now(), "2018-07-26 14:00:00 ~ 2018-07-26 15:00:00", 77));

        List<BaseTypeCategory> baseTypeCategories = new ArrayList<>();
        baseTypeCategories.add(new BaseTypeCategory(1, BaseSubType.START_DATE.name(), ""));
    }

//    @SuppressWarnings("unchecked")
//    @Test
//    public <G extends GraphModel> void groupingByBaseType() {
//        List<G> graphModelList = new ArrayList<>();
//        graphModelList.add((G) new XyGraph(1, 1, LocalDateTime.now(), "2018-07-26 14:00:00 ~ 2018-07-26 15:00:00", 10));
//        graphModelList.add((G) new XyGraph(1, 1, LocalDateTime.now(), "2018-07-26 16:00:00 ~ 2018-07-26 17:00:00", 190));
//        graphModelList.add((G) new XyGraph(1, 1, LocalDateTime.now(), "2018-07-26 15:00:00 ~ 2018-07-26 16:00:00", 210));
//        graphModelList.add((G) new XyGraph(1, 1, LocalDateTime.now(), "2018-07-26 14:00:00 ~ 2018-07-26 15:00:00", 77));
//
//        List<G> map = graphService.groupByBaseType(graphModelList);
//        logger.debug(String.valueOf(map));
//    }

}
