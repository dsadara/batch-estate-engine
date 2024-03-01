package com.dsadara.realestatebatchservice.service;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GenerateApiQueryParamRealTest {

    @Autowired
    private GenerateApiQueryParam generateApiQueryParam;

    @Test
    public void parseBjdCodeToMap_Success() throws Exception {
        //when
        List<String> bjdCodeList = generateApiQueryParam.getBjdCodeList();

        //then
        Assertions.assertNotNull(bjdCodeList);
        Assertions.assertEquals(439, bjdCodeList.size());
    }

    @Test
    public void generateDealYearMonth_Success() {
        //given
        Period period = Period.between(LocalDate.of(2005, 1, 1), LocalDate.now());
        int periodMonths = period.getYears() * 12 + period.getMonths();

        //when
        List<String> dealYearMonthsList = generateApiQueryParam.getDealYearMonthsList();

        //then
        Assertions.assertNotNull(dealYearMonthsList);
        Assertions.assertEquals(periodMonths + 1, dealYearMonthsList.size());
    }

}