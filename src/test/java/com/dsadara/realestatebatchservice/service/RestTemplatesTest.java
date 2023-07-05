package com.dsadara.realestatebatchservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@SpringBootTest
public class RestTemplatesTest {

    @Autowired
    private RestTemplates restTemplates;
    @Autowired
    private JsonDeserializer jsonDeserializer;

    @Test
    @DisplayName("성공-아파트 전월세 Json 데이터 가져오기")
    void ApartmentRent_Success_JsonData() throws JsonProcessingException {
        ResponseEntity<String> response;

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("LAWD_CD", "11500");
        queryParams.add("DEAL_YMD", "202304");
        queryParams.add("serviceKey", "KNxUoxDnwzkyp3fb8dOjCWatfWm6VdGxJHzwOlvkSAcOcm%2B6%2BgIsOrcZ8Wr8hU0qzcmNE2tSjG7HUQBIA%2FqkYg%3D%3D");

        response = restTemplates.getResponse(
                "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptRent?",
                queryParams
        );

        JsonNode root = jsonDeserializer.stringToJsonNode(response.getBody());
        JsonNode responseAttr = root.path("response");
        JsonNode body = responseAttr.path("body");
        JsonNode totalCount = body.path("totalCount");

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotEquals(0, totalCount.asInt());

    }

    @Test
    @DisplayName("실패-아파트 전월세 Json 데이터 가져오기-지역코드 없음")
    void ApartmentRent_Fail_WrongRegionCode_JsonData() throws JsonProcessingException {
        ResponseEntity<String> response;

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("LAWD_CD", "wrongCode");
        queryParams.add("DEAL_YMD", "202304");
        queryParams.add("serviceKey", "KNxUoxDnwzkyp3fb8dOjCWatfWm6VdGxJHzwOlvkSAcOcm%2B6%2BgIsOrcZ8Wr8hU0qzcmNE2tSjG7HUQBIA%2FqkYg%3D%3D");

        response = restTemplates.getResponse(
                "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptRent?",
                queryParams
        );

        JsonNode root = jsonDeserializer.stringToJsonNode(response.getBody());
        JsonNode responseAttr = root.path("response");
        JsonNode header = responseAttr.path("header");
        JsonNode totalCount = header.path("totalCount");

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(0, totalCount.asInt());
    }

    @Test
    @DisplayName("실패-아파트 전월세 Json 데이터 가져오기-searchKey 없음")
    void ApartmentRent_Fail_NoSearchKey_JsonData() throws JsonProcessingException {
        ResponseEntity<String> response;

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("LAWD_CD", "11500");
        queryParams.add("DEAL_YMD", "202304");
        queryParams.add("serviceKey", null);

        response = restTemplates.getResponse(
                "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptRent?",
                queryParams
        );

        JsonNode root = jsonDeserializer.stringToJsonNode(response.getBody());
        JsonNode responseAttr = root.path("response");
        JsonNode header = responseAttr.path("header");
        JsonNode resultCode = header.path("resultCode");

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("99", resultCode.asText());
    }
}
