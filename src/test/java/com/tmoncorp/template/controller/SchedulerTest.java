package com.tmoncorp.template.controller;

import com.tmoncorp.operator.datagenerator.MockInquiryInfoGenerator;
import com.tmoncorp.operator.model.mockdata.*;
import com.tmoncorp.operator.model.mockdata.type.*;
import com.tmoncorp.operator.model.operateddata.DataOfTime;
import com.tmoncorp.operator.model.operateddata.DataOfTypes;
import com.tmoncorp.operator.task.Scheduler;
import com.tmoncorp.service.model.type.DataType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.tmoncorp.operator.model.mockdata.type.CategoryName.CULTURE;
import static com.tmoncorp.operator.model.mockdata.type.CategoryName.LOCAL;
import static com.tmoncorp.operator.model.mockdata.type.CategoryName.TOUR;
import static com.tmoncorp.operator.model.mockdata.type.DealName.*;
import static com.tmoncorp.operator.model.mockdata.type.InquiryType.QUERY;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( {"classpath:applicationContext-bean.xml", "classpath:spring/applicationContext-config.xml", "classpath:spring/applicationContext-database.xml", "classpath:spring/applicationContext-transaction.xml"})
public class SchedulerTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<User> mockUsers;
    private List<Deal> mockDeals;
    private List<LocalDateTime> mockTimes;

    private List<SalesInfo> mockSalesInfo;
    private List<InquiryInfo> mockInquiryInfo;
    private List<VisitInfo> mockVisitInfo;

    @Autowired
    MockInquiryInfoGenerator mockInquiryInfoGenerator;

    @Autowired
    Scheduler scheduler;

    @Before
    public void beforeTest() {
        mockSalesInfo = new ArrayList<>();
        mockInquiryInfo = new ArrayList<>();
        mockVisitInfo = new ArrayList<>();

        mockUsers = new ArrayList<>();
        mockUsers.add(new User(0, Gender.MALE, Region.REGION_1, Age.AGE_10));   //1 , 50000,    2018-08-01 18:00:00 ~ 2018-08-01 19:00:00
        mockUsers.add(new User(1, Gender.MALE, Region.REGION_3, Age.AGE_40));   //2 , 40000,    2018-08-01 18:00:00 ~ 2018-08-01 19:00:00
        mockUsers.add(new User(2, Gender.FEMALE, Region.REGION_1, Age.AGE_10)); //3 , 600000,   2018-08-01 19:00:00 ~ 2018-08-01 20:00:00
        mockUsers.add(new User(3, Gender.FEMALE, Region.REGION_2, Age.AGE_50)); //4 , 5000,     2018-08-02 01:00:00 ~ 2018-08-02 02:00:00
        mockUsers.add(new User(4, Gender.MALE, Region.REGION_1, Age.AGE_10));   //5 , 100000,   2018-08-02 01:00:00 ~ 2018-08-02 02:00:00

        mockDeals = new ArrayList<>();
        mockDeals.add(new Deal(0, LOCAL, GYM, 50000));
        mockDeals.add(new Deal(1, CULTURE, MUSICAL, 40000));
        mockDeals.add(new Deal(2, TOUR, INTERNATIONAL, 600000));
        mockDeals.add(new Deal(3, CULTURE, RESTAURANT, 5000));
        mockDeals.add(new Deal(4, TOUR, DOMESTIC, 100000));
//        mockUsers.add(new User(5, Gender.MALE, Region.REGION_6, Age.AGE_20));

        mockTimes = new ArrayList<>();
        mockTimes.add(LocalDateTime.of(2018, 8, 1, 18, 0, 0));
        mockTimes.add(LocalDateTime.of(2018, 8, 1, 18, 0, 1));
        mockTimes.add(LocalDateTime.of(2018, 8, 1, 19, 0, 0));
        mockTimes.add(LocalDateTime.of(2018, 8, 2, 1, 1, 57));
        mockTimes.add(LocalDateTime.of(2018, 8, 2, 1, 11, 13));

        int k = 0;
        for (int i = 0; i < 5; i++) {
            SalesInfo salesInfo = new SalesInfo(0, i + 1);
            InquiryInfo inquiryInfo = new InquiryInfo(0, InquiryType.getInquiryTypeFromIndex(k));
            VisitInfo visitInfo = new VisitInfo(0, Platform.getPlatformFromIndex(k));

            if (k == 0) {
                k = 1;
            } else {
                k = 0;
            }

            salesInfo.setUser(mockUsers.get(i));
            salesInfo.setDeal(mockDeals.get(i));
            salesInfo.setDate(mockTimes.get(i));

            inquiryInfo.setUser(mockUsers.get(i));
            inquiryInfo.setDeal(mockDeals.get(i));
            inquiryInfo.setDate(mockTimes.get(i));

            visitInfo.setUser(mockUsers.get(i));
            visitInfo.setDeal(mockDeals.get(i));
            visitInfo.setDate(mockTimes.get(i));

            mockSalesInfo.add(salesInfo);
            mockInquiryInfo.add(inquiryInfo);
            mockVisitInfo.add(visitInfo);
        }
    }

    @Test
    public void testOperateOnDataBasedOnTypesForSalesInfo() {
        List<DataOfTypes> operatedDataOfTypesList = scheduler.operateOnDataBasedOnTypes(mockSalesInfo);
        for (DataOfTypes dataOfTypes : operatedDataOfTypesList) {
            String type = dataOfTypes.getBaseType();
            switch (type) {
                case "MALE":
                    if (dataOfTypes.getDataType() == DataType.SOLD_COUNT) {
                        assertEquals(8, dataOfTypes.getDataValue());
                    } else {
                        assertEquals(630000, dataOfTypes.getDataValue());
                    }
                    break;
                case "FEMALE":
                    if (dataOfTypes.getDataType() == DataType.SOLD_COUNT) {
                        assertEquals(7, dataOfTypes.getDataValue());
                    } else {
                        assertEquals(1820000, dataOfTypes.getDataValue());
                    }
                    break;
                case "REGION_1":
                    if (dataOfTypes.getDataType() == DataType.SOLD_COUNT) {
                        assertEquals(9, dataOfTypes.getDataValue());
                    } else {
                        assertEquals(2350000, dataOfTypes.getDataValue());
                    }
                    break;
                case "REGION_2":
                    if (dataOfTypes.getDataType() == DataType.SOLD_COUNT) {
                        assertEquals(4, dataOfTypes.getDataValue());
                    } else {
                        assertEquals(20000, dataOfTypes.getDataValue());
                    }
                    break;
                case "REGION_3":
                    if (dataOfTypes.getDataType() == DataType.SOLD_COUNT) {
                        assertEquals(2, dataOfTypes.getDataValue());
                    } else {
                        assertEquals(80000, dataOfTypes.getDataValue());
                    }
                    break;
                case "REGION_4":
                    if (dataOfTypes.getDataType() == DataType.SOLD_COUNT) {
                        assertEquals(0, dataOfTypes.getDataValue());
                    } else {
                        assertEquals(0, dataOfTypes.getDataValue());
                    }
                    break;
                case "REGION_5":
                    if (dataOfTypes.getDataType() == DataType.SOLD_COUNT) {
                        assertEquals(0, dataOfTypes.getDataValue());
                    } else {
                        assertEquals(0, dataOfTypes.getDataValue());
                    }
                    break;
                case "AGE_10":
                    if (dataOfTypes.getDataType() == DataType.SOLD_COUNT) {
                        assertEquals(9, dataOfTypes.getDataValue());
                    } else {
                        assertEquals(2350000, dataOfTypes.getDataValue());
                    }
                    break;
                case "AGE_20":
                    if (dataOfTypes.getDataType() == DataType.SOLD_COUNT) {
                        assertEquals(0, dataOfTypes.getDataValue());
                    } else {
                        assertEquals(0, dataOfTypes.getDataValue());
                    }
                    break;
                case "AGE_30":
                    if (dataOfTypes.getDataType() == DataType.SOLD_COUNT) {
                        assertEquals(0, dataOfTypes.getDataValue());
                    } else {
                        assertEquals(0, dataOfTypes.getDataValue());
                    }
                    break;
                case "AGE_40":
                    if (dataOfTypes.getDataType() == DataType.SOLD_COUNT) {
                        assertEquals(2, dataOfTypes.getDataValue());
                    } else {
                        assertEquals(80000, dataOfTypes.getDataValue());
                    }
                    break;
                case "AGE_50":
                    if (dataOfTypes.getDataType() == DataType.SOLD_COUNT) {
                        assertEquals(4, dataOfTypes.getDataValue());
                    } else {
                        assertEquals(20000, dataOfTypes.getDataValue());
                    }
                    break;
                default:
                    logger.error("정의되지 않은 mock data type 입니다");
            }
        }
    }

    @Test
    public void testOperateOnDataBasedOnTypesForInquiryInfo() {
        List<DataOfTypes> operatedDataOfTypesList = scheduler.operateOnDataBasedOnTypes(mockInquiryInfo);
        for (DataOfTypes dataOfTypes : operatedDataOfTypesList) {
            String type = dataOfTypes.getBaseType();
            switch (type) {
                case "MALE":
                    assertEquals(3, dataOfTypes.getDataValue());
                    break;
                case "FEMALE":
                    assertEquals(2, dataOfTypes.getDataValue());
                    break;
                case "REGION_1":
                    assertEquals(3, dataOfTypes.getDataValue());
                    break;
                case "REGION_2":
                    assertEquals(1, dataOfTypes.getDataValue());
                    break;
                case "REGION_3":
                    assertEquals(1, dataOfTypes.getDataValue());
                    break;
                case "REGION_4":
                    assertEquals(0, dataOfTypes.getDataValue());
                    break;
                case "REGION_5":
                    assertEquals(0, dataOfTypes.getDataValue());
                    break;
                case "AGE_10":
                    assertEquals(3, dataOfTypes.getDataValue());
                    break;
                case "AGE_20":
                    assertEquals(0, dataOfTypes.getDataValue());
                    break;
                case "AGE_30":
                    assertEquals(0, dataOfTypes.getDataValue());
                    break;
                case "AGE_40":
                    assertEquals(1, dataOfTypes.getDataValue());
                    break;
                case "AGE_50":
                    assertEquals(1, dataOfTypes.getDataValue());
                    break;
                case "QUERY":
                    assertEquals(3, dataOfTypes.getDataValue());
                    break;
                case "COMPLAINT":
                    assertEquals(2, dataOfTypes.getDataValue());
                    break;
                case "CULTURE":
                    assertEquals(2, dataOfTypes.getDataValue());
                    break;
                case "LOCAL":
                    assertEquals(1, dataOfTypes.getDataValue());
                    break;
                case "TOUR":
                    assertEquals(2, dataOfTypes.getDataValue());
                    break;
                default:
                    logger.error("정의되지 않은 mock data type 입니다");
            }
        }
    }

    @Test
    public void testOperateOnDataBasedOnTypesForVisitInfo() {
        List<DataOfTypes> operatedDataOfTypesList = scheduler.operateOnDataBasedOnTypes(mockVisitInfo);
        for (DataOfTypes dataOfTypes : operatedDataOfTypesList) {
            String type = dataOfTypes.getBaseType();
            switch (type) {
                case "MALE":
                    assertEquals(3, dataOfTypes.getDataValue());
                    break;
                case "FEMALE":
                    assertEquals(2, dataOfTypes.getDataValue());
                    break;
                case "REGION_1":
                    assertEquals(3, dataOfTypes.getDataValue());
                    break;
                case "REGION_2":
                    assertEquals(1, dataOfTypes.getDataValue());
                    break;
                case "REGION_3":
                    assertEquals(1, dataOfTypes.getDataValue());
                    break;
                case "REGION_4":
                    assertEquals(0, dataOfTypes.getDataValue());
                    break;
                case "REGION_5":
                    assertEquals(0, dataOfTypes.getDataValue());
                    break;
                case "AGE_10":
                    assertEquals(3, dataOfTypes.getDataValue());
                    break;
                case "AGE_20":
                    assertEquals(0, dataOfTypes.getDataValue());
                    break;
                case "AGE_30":
                    assertEquals(0, dataOfTypes.getDataValue());
                    break;
                case "AGE_40":
                    assertEquals(1, dataOfTypes.getDataValue());
                    break;
                case "AGE_50":
                    assertEquals(1, dataOfTypes.getDataValue());
                    break;
                case "WEB":
                    assertEquals(3, dataOfTypes.getDataValue());
                    break;
                case "MOBILE":
                    assertEquals(2, dataOfTypes.getDataValue());
                    break;
                default:
                    logger.error("정의되지 않은 mock data type 입니다");
            }
        }
    }

    @Test
    public void testOperateOnDataBasedOnTimeForSalesInfo() {
        List<DataOfTime> operatedDateOfTimeList = scheduler.operateOnDataBasedOnTime(mockSalesInfo);
        for (DataOfTime dataOfTime : operatedDateOfTimeList) {
            String timeRange = dataOfTime.getTimeRange();
            switch (timeRange) {
                case "2018-08-01 18:00:00 ~ 2018-08-01 19:00:00":
                    if(dataOfTime.getDataType() == DataType.SOLD_COUNT)
                        assertEquals(3, dataOfTime.getDataValue());
                    else
                        assertEquals(130000, dataOfTime.getDataValue());
                    break;
                case "2018-08-01 19:00:00 ~ 2018-08-01 20:00:00":
                    if(dataOfTime.getDataType() == DataType.SOLD_COUNT)
                        assertEquals(3, dataOfTime.getDataValue());
                    else
                        assertEquals(1800000, dataOfTime.getDataValue());
                    break;
                case "2018-08-01 20:00:00 ~ 2018-08-01 21:00:00":
                    if(dataOfTime.getDataType() == DataType.SOLD_COUNT)
                        assertEquals(0, dataOfTime.getDataValue());
                    else
                        assertEquals(0, dataOfTime.getDataValue());
                    break;
                case "2018-08-01 21:00:00 ~ 2018-08-01 22:00:00":
                    if(dataOfTime.getDataType() == DataType.SOLD_COUNT)
                        assertEquals(0, dataOfTime.getDataValue());
                    else
                        assertEquals(0, dataOfTime.getDataValue());
                    break;
                case "2018-08-01 22:00:00 ~ 2018-08-01 23:00:00":
                    if(dataOfTime.getDataType() == DataType.SOLD_COUNT)
                        assertEquals(0, dataOfTime.getDataValue());
                    else
                        assertEquals(0, dataOfTime.getDataValue());
                    break;
                case "2018-08-01 23:00:00 ~ 2018-08-02 00:00:00":
                    if(dataOfTime.getDataType() == DataType.SOLD_COUNT)
                        assertEquals(0, dataOfTime.getDataValue());
                    else
                        assertEquals(0, dataOfTime.getDataValue());
                    break;
                case "2018-08-02 00:00:00 ~ 2018-08-02 01:00:00":
                    if(dataOfTime.getDataType() == DataType.SOLD_COUNT)
                        assertEquals(0, dataOfTime.getDataValue());
                    else
                        assertEquals(0, dataOfTime.getDataValue());
                    break;
                case "2018-08-02 01:00:00 ~ 2018-08-02 02:00:00":
                    if(dataOfTime.getDataType() == DataType.SOLD_COUNT)
                        assertEquals(9, dataOfTime.getDataValue());
                    else
                        assertEquals(520000, dataOfTime.getDataValue());
                    break;
                default:
                    logger.error("이 시간대 " + timeRange + " 는 존재해서는 안 됩니다");
            }
        }
    }

    @Test
    public void testOperateOnDataBasedOnTimeForVisitInfo() {
        List<DataOfTime> operatedDateOfTimeList = scheduler.operateOnDataBasedOnTime(mockVisitInfo);
        for (DataOfTime dataOfTime : operatedDateOfTimeList) {
            String timeRange = dataOfTime.getTimeRange();
            switch (timeRange) {
                case "2018-08-01 18:00:00 ~ 2018-08-01 19:00:00":
                    assertEquals(2, dataOfTime.getDataValue());
                    break;
                case "2018-08-01 19:00:00 ~ 2018-08-01 20:00:00":
                    assertEquals(1, dataOfTime.getDataValue());
                    break;
                case "2018-08-01 20:00:00 ~ 2018-08-01 21:00:00":
                    assertEquals(0, dataOfTime.getDataValue());
                    break;
                case "2018-08-01 21:00:00 ~ 2018-08-01 22:00:00":
                    assertEquals(0, dataOfTime.getDataValue());
                    break;
                case "2018-08-01 22:00:00 ~ 2018-08-01 23:00:00":
                    assertEquals(0, dataOfTime.getDataValue());
                    break;
                case "2018-08-01 23:00:00 ~ 2018-08-02 00:00:00":
                    assertEquals(0, dataOfTime.getDataValue());
                    break;
                case "2018-08-02 00:00:00 ~ 2018-08-02 01:00:00":
                    assertEquals(0, dataOfTime.getDataValue());
                    break;
                case "2018-08-02 01:00:00 ~ 2018-08-02 02:00:00":
                    assertEquals(2, dataOfTime.getDataValue());
                    break;
                default:
                    logger.error("이 시간대 " + timeRange + " 는 존재해서는 안 됩니다");
            }
        }
    }
}
