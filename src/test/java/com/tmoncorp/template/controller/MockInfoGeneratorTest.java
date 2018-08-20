package com.tmoncorp.template.controller;

import com.tmoncorp.operator.datagenerator.RandomAssigner;
import com.tmoncorp.operator.model.mockdata.InquiryInfo;
import com.tmoncorp.operator.model.mockdata.SalesInfo;
import com.tmoncorp.operator.model.mockdata.VisitInfo;
import com.tmoncorp.operator.datagenerator.MockInquiryInfoGenerator;
import com.tmoncorp.operator.datagenerator.MockSalesInfoGenerator;
import com.tmoncorp.operator.datagenerator.MockVisitInfoGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.Random;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( {"classpath:applicationContext-bean.xml", "classpath:spring/applicationContext-config.xml", "classpath:spring/applicationContext-database.xml", "classpath:spring/applicationContext-transaction.xml"})
public class MockInfoGeneratorTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    LocalDateTime nowInTime = LocalDateTime.of(2018, 8, 7, 13, 26, 50);
    String delay = "5000";

    @Autowired
    private MockInquiryInfoGenerator mockInquiryInfoGenerator;
    @Autowired
    private MockSalesInfoGenerator mockSalesInfoGenerator;
    @Autowired
    private MockVisitInfoGenerator mockVisitInfoGenerator;

    @Test
    public void testMockInquiryInfoGenerator() {
        RandomAssigner randomAssigner = new RandomAssigner();
        randomAssigner.setStartTime(nowInTime);
        randomAssigner.setInterval(Integer.parseInt(delay));
        InquiryInfo inquiryInfo = mockInquiryInfoGenerator.generateMockInquiryInfo(randomAssigner);
        assertTrue(inquiryInfo.isNotNull());
        logger.debug("원본 문의 데이터 하나 생성 : " + inquiryInfo);
    }

    @Test
    public void testMockSalesInfoGenerator() {
        RandomAssigner randomAssigner = new RandomAssigner();
        randomAssigner.setStartTime(nowInTime);
        randomAssigner.setInterval(Integer.parseInt(delay));
        SalesInfo salesInfo = mockSalesInfoGenerator.generateMockSalesInfo(randomAssigner);
        assertTrue(salesInfo.isNotNull());
        logger.debug("원본 판매 데이터 하나 생성 : " + salesInfo);

    }

    @Test
    public void testMockVisitInfoGenerator() {
        RandomAssigner randomAssigner = new RandomAssigner();
        randomAssigner.setStartTime(nowInTime);
        randomAssigner.setInterval(Integer.parseInt(delay));
        VisitInfo visitInfo = mockVisitInfoGenerator.generateMockVisitInfo(randomAssigner);
        assertTrue(visitInfo.isNotNull());
        logger.debug("원본 방문 데이터 하나 생성 : " + visitInfo);

    }
}
