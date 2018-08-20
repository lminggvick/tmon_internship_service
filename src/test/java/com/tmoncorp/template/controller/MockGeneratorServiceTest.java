package com.tmoncorp.template.controller;

import com.tmoncorp.operator.controller.MockGeneratorController;
import com.tmoncorp.operator.model.mockdata.InquiryInfo;
import com.tmoncorp.operator.model.mockdata.SalesInfo;
import com.tmoncorp.operator.model.mockdata.VisitInfo;
import com.tmoncorp.operator.repository.MockDataRepository;
import com.tmoncorp.operator.service.CUDResult;
import com.tmoncorp.operator.service.MockDataService;
import com.tmoncorp.operator.service.MockGeneratorService;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * Created by jhcorea on 2018. 7. 20..
 * 단점 - 오래걸린다. 서비스에 올라오는 빈을 다띄워야하니까
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( {"classpath:applicationContext-bean.xml", "classpath:spring/applicationContext-config.xml", "classpath:spring/applicationContext-database.xml", "classpath:spring/applicationContext-transaction.xml"})
public class MockGeneratorServiceTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String now = "2018-08-07 13:26:50";
    private String delay = "5000";

    @Autowired
    ApplicationContext context;

    @Autowired
    MockGeneratorService mockGeneratorService;

    @Test
    public void testGenerateMockInquiryInfo() {
        List<InquiryInfo> mockInquriyInfo = mockGeneratorService.generateMockInquriyInfo(now, delay);
        assertNotNull(mockInquriyInfo);
        logger.debug("원본 문의 데이터 생성 : " + mockInquriyInfo);
    }

    @Test
    public void testGenerateMockSalesInfo() {
        List<SalesInfo> mockSalesInfo = mockGeneratorService.generateMockSalesInfo(now, delay);
        assertNotNull(mockSalesInfo);
        logger.debug("원본 판매 데이터 생성 : " + mockSalesInfo);

    }

    @Test
    public void testGenerateMockVisitInfo() {
        List<VisitInfo> mockVisitInfo = mockGeneratorService.generateMockVisitInfo(now, delay);
        assertNotNull(mockVisitInfo);
        logger.debug("원본 방문 데이터 생성 : " + mockVisitInfo);

    }
}
