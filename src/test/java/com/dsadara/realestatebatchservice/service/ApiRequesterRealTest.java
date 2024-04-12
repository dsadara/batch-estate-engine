package com.dsadara.realestatebatchservice.service;

import com.dsadara.realestatebatchservice.dto.RealEstateDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ApiRequesterRealTest {

    @Autowired
    private ApiRequester apiRequester;

    @Test
    @DisplayName("성공-requestData()-아파트 전월세 api 요청")
    public void requestData_Success_AptRent() throws Exception {
        //given
        String baseUrl = "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptRent";
        String bjdCode = "11200";
        String dealYearMonth = "202206";
        String serviceKey = "KNxUoxDnwzkyp3fb8dOjCWatfWm6VdGxJHzwOlvkSAcOcm%2B6%2BgIsOrcZ8Wr8hU0qzcmNE2tSjG7HUQBIA%2FqkYg%3D%3D";

        //when
        List<RealEstateDto> dtos = apiRequester.fetchData(baseUrl, serviceKey, bjdCode, dealYearMonth);

        //then
        assertNotNull(dtos);
        assertNotEquals(0, dtos.size());
        assertNotNull(dtos.get(0).getRequestRenewalRight());
        assertNotNull(dtos.get(0).getConstructYear());
        assertNotNull(dtos.get(0).getContractType());
        assertNotNull(dtos.get(0).getContractPeriod());
        assertNotNull(dtos.get(0).getContractYear());
        assertNotNull(dtos.get(0).getLegalDong());
        assertNotNull(dtos.get(0).getMonthlyRent());
        assertNotNull(dtos.get(0).getName());
        assertNotNull(dtos.get(0).getMonth());
        assertNotNull(dtos.get(0).getDay());
        assertNotNull(dtos.get(0).getJeonYongArea());
        assertNotNull(dtos.get(0).getDeposit());
        assertNotNull(dtos.get(0).getDepositBefore());
        assertNotNull(dtos.get(0).getParcelNumber());
        assertNotNull(dtos.get(0).getRegionCode());
        assertNotNull(dtos.get(0).getFloor());
        assertNotNull(dtos.get(0).getMonthlyRentBefore());
    }

    @Test
    @DisplayName("성공-requestData()-아파트 매매 api 요청")
    public void requestData_Success_AptTransaction() throws Exception {
        //given
        String baseUrl = "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTrade";
        String bjdCode = "11200";
        String dealYearMonth = "202007";
        String serviceKey = "KNxUoxDnwzkyp3fb8dOjCWatfWm6VdGxJHzwOlvkSAcOcm%2B6%2BgIsOrcZ8Wr8hU0qzcmNE2tSjG7HUQBIA%2FqkYg%3D%3D";

        //when
        List<RealEstateDto> dtos = apiRequester.fetchData(baseUrl, serviceKey, bjdCode, dealYearMonth);

        //then
        assertNotNull(dtos);
        assertNotNull(dtos.get(0));
        assertNotEquals(0, dtos.size());
        assertNotNull(dtos.get(0).getConstructYear());
        assertNotNull(dtos.get(0).getContractYear());
        assertNotNull(dtos.get(0).getLegalDong());
        assertNotNull(dtos.get(0).getName());
        assertNotNull(dtos.get(0).getMonth());
        assertNotNull(dtos.get(0).getDay());
        assertNotNull(dtos.get(0).getJeonYongArea());
        assertNotNull(dtos.get(0).getParcelNumber());
        assertNotNull(dtos.get(0).getRegionCode());
        assertNotNull(dtos.get(0).getDealAmount());
        assertNotNull(dtos.get(0).getCancelDealType());
        assertNotNull(dtos.get(0).getCancelDealDay());
        assertNotNull(dtos.get(0).getDealType());
        assertNotNull(dtos.get(0).getAgentAddress());
    }

    @Test
    @DisplayName("성공-requestData()-단독 다가구 api 요청")
    public void requestData_Success_DetachedHouseRent() throws Exception {
        //given
        String baseUrl = "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcSHRent";
        String bjdCode = "11200";
        String dealYearMonth = "202206";
        String serviceKey = "KNxUoxDnwzkyp3fb8dOjCWatfWm6VdGxJHzwOlvkSAcOcm%2B6%2BgIsOrcZ8Wr8hU0qzcmNE2tSjG7HUQBIA%2FqkYg%3D%3D";

        //when
        List<RealEstateDto> dtos = apiRequester.fetchData(baseUrl, serviceKey, bjdCode, dealYearMonth);

        //then
        assertNotNull(dtos);
        assertNotEquals(0, dtos.size());
        assertNotNull(dtos.get(0).getRequestRenewalRight());
        assertNotNull(dtos.get(0).getConstructYear());
        assertNotNull(dtos.get(0).getContractType());
        assertNotNull(dtos.get(0).getContractPeriod());
        assertNotNull(dtos.get(0).getJeonYongArea());
        assertNotNull(dtos.get(0).getContractYear());
        assertNotNull(dtos.get(0).getLegalDong());
        assertNotNull(dtos.get(0).getDeposit());
        assertNotNull(dtos.get(0).getMonth());
        assertNotNull(dtos.get(0).getMonthlyRent());
        assertNotNull(dtos.get(0).getDay());
        assertNotNull(dtos.get(0).getDepositBefore());
        assertNotNull(dtos.get(0).getMonthlyRentBefore());
        assertNotNull(dtos.get(0).getRegionCode());
    }

    @Test
    @DisplayName("성공-requestData()- 연립 다세대 api 요청")
    public void requestData_Success_RowHouseRent() throws Exception {
        //given
        String baseUrl = "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcRHRent";
        String bjdCode = "11200";
        String dealYearMonth = "202206";
        String serviceKey = "KNxUoxDnwzkyp3fb8dOjCWatfWm6VdGxJHzwOlvkSAcOcm%2B6%2BgIsOrcZ8Wr8hU0qzcmNE2tSjG7HUQBIA%2FqkYg%3D%3D";

        //when
        List<RealEstateDto> dtos = apiRequester.fetchData(baseUrl, serviceKey, bjdCode, dealYearMonth);

        //then
        assertNotNull(dtos);
        assertNotEquals(0, dtos.size());
        assertNotNull(dtos.get(0).getRequestRenewalRight());
        assertNotNull(dtos.get(0).getConstructYear());
        assertNotNull(dtos.get(0).getContractType());
        assertNotNull(dtos.get(0).getContractPeriod());
        assertNotNull(dtos.get(0).getContractYear());
        assertNotNull(dtos.get(0).getLegalDong());
        assertNotNull(dtos.get(0).getDeposit());
        assertNotNull(dtos.get(0).getName());
        assertNotNull(dtos.get(0).getMonth());
        assertNotNull(dtos.get(0).getMonthlyRent());
        assertNotNull(dtos.get(0).getDay());
        assertNotNull(dtos.get(0).getJeonYongArea());
        assertNotNull(dtos.get(0).getDepositBefore());
        assertNotNull(dtos.get(0).getMonthlyRentBefore());
        assertNotNull(dtos.get(0).getParcelNumber());
        assertNotNull(dtos.get(0).getRegionCode());
        assertNotNull(dtos.get(0).getFloor());
    }

    @Test
    @DisplayName("성공-requestData()- 오피스텔 api 요청")
    public void requestData_Success_OfficeTel() throws Exception {
        //given
        String baseUrl = "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcOffiRent";
        String bjdCode = "11200";
        String dealYearMonth = "202206";
        String serviceKey = "KNxUoxDnwzkyp3fb8dOjCWatfWm6VdGxJHzwOlvkSAcOcm%2B6%2BgIsOrcZ8Wr8hU0qzcmNE2tSjG7HUQBIA%2FqkYg%3D%3D";

        //when
        List<RealEstateDto> dtos = apiRequester.fetchData(baseUrl, serviceKey, bjdCode, dealYearMonth);

        //then
        assertNotNull(dtos);
        assertNotEquals(0, dtos.size());
        assertNotNull(dtos.get(0).getRequestRenewalRight());
        assertNotNull(dtos.get(0).getConstructYear());
        assertNotNull(dtos.get(0).getContractType());
        assertNotNull(dtos.get(0).getContractPeriod());
        assertNotNull(dtos.get(0).getContractYear());
        assertNotNull(dtos.get(0).getName());
        assertNotNull(dtos.get(0).getLegalDong());
        assertNotNull(dtos.get(0).getDeposit());
        assertNotNull(dtos.get(0).getSiGunGu());
        assertNotNull(dtos.get(0).getMonth());
        assertNotNull(dtos.get(0).getMonthlyRent());
        assertNotNull(dtos.get(0).getDay());
        assertNotNull(dtos.get(0).getJeonYongArea());
        assertNotNull(dtos.get(0).getDepositBefore());
        assertNotNull(dtos.get(0).getMonthlyRentBefore());
        assertNotNull(dtos.get(0).getParcelNumber());
        assertNotNull(dtos.get(0).getRegionCode());
        assertNotNull(dtos.get(0).getFloor());
    }

}
