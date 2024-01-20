package com.dsadara.realestatebatchservice.service;

import com.dsadara.realestatebatchservice.dto.RealEstateDto;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RequestData {

    private final JsonDeserializer jsonDeserializer;
    private final GenerateApiQueryParam generateApiQueryParam;

    public RequestData(JsonDeserializer jsonDeserializer, GenerateApiQueryParam generateApiQueryParam) {
        this.jsonDeserializer = jsonDeserializer;
        this.generateApiQueryParam = generateApiQueryParam;
    }

    public List<RealEstateDto> requestData(String baseURL, String legalDongCode, String contractYMD, String servicekey) throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("LAWD_CD", legalDongCode);
        queryParams.add("DEAL_YMD", contractYMD);
        queryParams.add("serviceKey", servicekey);

        ResponseEntity<String> response = jsonDeserializer.getResponse(baseURL, queryParams);
        Optional<JsonNode> itemOptional = jsonDeserializer.stringToJsonNode(response.getBody());
        List<RealEstateDto> realEstateDtos = jsonDeserializer.jsonNodeToPOJO(itemOptional).orElse(new ArrayList<>());
        log.info("[법정동 코드 {}][계약 연월일 {}] api 호출 완료 -> ( 데이터 개수: {} )", legalDongCode, contractYMD, realEstateDtos.size());

        return realEstateDtos;
    }

    public List<RealEstateDto> requestOneLegalDongData(String baseURL, String legalDongCode, String servicekey) throws Exception {
        List<RealEstateDto> result = new ArrayList<>();
        for (String dealYearMonth : generateApiQueryParam.getDealYearMonthsList()) {
            result.addAll(requestData(baseURL, legalDongCode, dealYearMonth, servicekey));
        }
        return result;
    }

    public List<RealEstateDto> requestAptRentData() throws Exception {
        List<String> bjdCodeList = generateApiQueryParam.getBjdCodeList();
        List<String> dealYearMonthsList = generateApiQueryParam.getDealYearMonthsList();
        List<RealEstateDto> result = new LinkedList<>();

        String baseUrl = "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptRent";
        String serviceKey = "KNxUoxDnwzkyp3fb8dOjCWatfWm6VdGxJHzwOlvkSAcOcm%2B6%2BgIsOrcZ8Wr8hU0qzcmNE2tSjG7HUQBIA%2FqkYg%3D%3D";

        for (String bjdCode : bjdCodeList) {
            log.info(String.format("[아파트 전월세][데이터 요청][법정동코드:%s]", bjdCode));
            for (String dealYearMonth : dealYearMonthsList) {
                result.addAll(requestData(baseUrl, bjdCode, dealYearMonth, serviceKey));
            }
        }

        return result;
    }

    public List<RealEstateDto> requestAptTradeData() throws Exception {
        List<String> bjdCodeList = generateApiQueryParam.getBjdCodeList();
        List<String> dealYearMonthsList = generateApiQueryParam.getDealYearMonthsList();
        List<RealEstateDto> result = new LinkedList<>();

        String baseUrl = "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTrade";
        String serviceKey = "KNxUoxDnwzkyp3fb8dOjCWatfWm6VdGxJHzwOlvkSAcOcm%2B6%2BgIsOrcZ8Wr8hU0qzcmNE2tSjG7HUQBIA%2FqkYg%3D%3D";

        for (String bjdCode : bjdCodeList) {
            log.info(String.format("[아파트 매매][데이터 요청][법정동코드:%s]", bjdCode));
            for (String dealYearMonth : dealYearMonthsList) {
                result.addAll(requestData(baseUrl, bjdCode, dealYearMonth, serviceKey));
            }
        }

        return result;
    }

    public List<RealEstateDto> requestRowHouseRent() throws Exception {
        List<String> bjdCodeList = generateApiQueryParam.getBjdCodeList();
        List<String> dealYearMonthsList = generateApiQueryParam.getDealYearMonthsList();
        List<RealEstateDto> result = new LinkedList<>();

        String baseUrl = "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcRHRent";
        String serviceKey = "KNxUoxDnwzkyp3fb8dOjCWatfWm6VdGxJHzwOlvkSAcOcm%2B6%2BgIsOrcZ8Wr8hU0qzcmNE2tSjG7HUQBIA%2FqkYg%3D%3D";

        for (String bjdCode : bjdCodeList) {
            log.info(String.format("[연립 다세대][데이터 요청][법정동코드:%s]", bjdCode));
            for (String dealYearMonth : dealYearMonthsList) {
                result.addAll(requestData(baseUrl, bjdCode, dealYearMonth, serviceKey));
            }
        }

        return result;
    }

    public List<RealEstateDto> requestEfficencyAptRent() throws Exception {
        List<String> bjdCodeList = generateApiQueryParam.getBjdCodeList();
        List<String> dealYearMonthsList = generateApiQueryParam.getDealYearMonthsList();
        List<RealEstateDto> result = new LinkedList<>();

        String baseUrl = "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcOffiRent";
        String serviceKey = "KNxUoxDnwzkyp3fb8dOjCWatfWm6VdGxJHzwOlvkSAcOcm%2B6%2BgIsOrcZ8Wr8hU0qzcmNE2tSjG7HUQBIA%2FqkYg%3D%3D";

        for (String bjdCode : bjdCodeList) {
            log.info(String.format("[오피스텔][데이터 요청][법정동코드:%s]", bjdCode));
            for (String dealYearMonth : dealYearMonthsList) {
                result.addAll(requestData(baseUrl, bjdCode, dealYearMonth, serviceKey));
            }
        }

        return result;
    }

    public List<RealEstateDto> requestDetachedHouseRent() throws Exception {
        List<String> bjdCodeList = generateApiQueryParam.getBjdCodeList();
        List<String> dealYearMonthsList = generateApiQueryParam.getDealYearMonthsList();
        List<RealEstateDto> result = new LinkedList<>();

        String baseUrl = "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcSHRent";
        String serviceKey = "KNxUoxDnwzkyp3fb8dOjCWatfWm6VdGxJHzwOlvkSAcOcm%2B6%2BgIsOrcZ8Wr8hU0qzcmNE2tSjG7HUQBIA%2FqkYg%3D%3D";

        for (String bjdCode : bjdCodeList) {
            log.info(String.format("[단독 다가구][데이터 요청][법정동코드:%s]", bjdCode));
            for (String dealYearMonth : dealYearMonthsList) {
                result.addAll(requestData(baseUrl, bjdCode, dealYearMonth, serviceKey));
            }
        }

        return result;
    }

}
