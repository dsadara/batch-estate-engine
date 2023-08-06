package com.dsadara.realestatebatchservice;

import com.dsadara.realestatebatchservice.aop.CustomStopwatch;
import com.dsadara.realestatebatchservice.dto.RealEstateDto;
import com.dsadara.realestatebatchservice.service.GenerateApiQueryParam;
import com.dsadara.realestatebatchservice.service.RequestData;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExecutionTimePrintTest {
    @Autowired
    private GenerateApiQueryParam generateApiQueryParam;
    @Autowired
    private RequestData requestData;
    @Autowired
    private CustomStopwatch customStopwatch;
    Logger logger = LoggerFactory.getLogger(ExecutionTimePrintTest.class);

    /**
     * ---------------------------------------------
     * ns         %     Task name
     * ---------------------------------------------
     * 033528917  007%  jsonNodeToPOJO
     * 027574334  006%  stringToJsonNode
     * 388372250  086%  getResponse
     * ---------------------------------------------
     * 449479208 ns  <= Total Execution Time
     * ---------------------------------------------
     * -> 파싱보다 api를 요청하는데 시간이 많이걸림
     */
    @Test
    @Ignore
    public void requestData_OneRequest() throws Exception {
        //given
        String baseUrl = "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptRent";
        String legalDongCode = "11200";
        String contractYMD = "202206";
        String serviceKey = "KNxUoxDnwzkyp3fb8dOjCWatfWm6VdGxJHzwOlvkSAcOcm%2B6%2BgIsOrcZ8Wr8hU0qzcmNE2tSjG7HUQBIA%2FqkYg%3D%3D";
        customStopwatch.reset();
        //when
        List<RealEstateDto> dtos = requestData.requestData(baseUrl, legalDongCode, contractYMD, serviceKey);
        //then
        logger.info(customStopwatch.prettyPrint());
    }

    /**
     * ---------------------------------------------
     * ns         %     Task name
     * ---------------------------------------------
     * 256353209  001%  jsonNodeToPOJO
     * 379197919  001%  stringToJsonNode
     * 43953431009  099%  getResponse
     * ---------------------------------------------
     * 44590038150 ns  <= Total Execution Time
     * ---------------------------------------------
     * 법정동 하나당 실행시간 44초 걸림
     * 전체 법정동을 요청하면 -> 44 * 439 = 19,316 = 321 minutes 56 seconds = 5 hours 21 minutes 56 seconds
     * 주택종류 5가지를 모두 요청하면 -> 5 hours 21 minutes 56 seconds * 5 = 26 hours 49 minutes 40 seconds
     * (요청시마다 다름 대략 계산한 것)
     */
    @Test
    @Ignore
    public void requestData_OneBjdCode() throws Exception {
        //given
        List<String> dealYearMonthsList = generateApiQueryParam.getDealYearMonthsList();
        String baseUrl = "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptRent";
        String legalDongCode = "11200";
        String serviceKey = "KNxUoxDnwzkyp3fb8dOjCWatfWm6VdGxJHzwOlvkSAcOcm%2B6%2BgIsOrcZ8Wr8hU0qzcmNE2tSjG7HUQBIA%2FqkYg%3D%3D";
        customStopwatch.reset();
        //when
        for (String dealYearhMonth : dealYearMonthsList) {
            List<RealEstateDto> dtos = requestData.requestData(baseUrl, legalDongCode, dealYearhMonth, serviceKey);
        }
        //then
        logger.info(customStopwatch.prettyPrint());
    }
}
