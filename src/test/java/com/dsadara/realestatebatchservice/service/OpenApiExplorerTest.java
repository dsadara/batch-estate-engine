package com.dsadara.realestatebatchservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.jdom2.JDOMException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

@SpringBootTest
public class OpenApiExplorerTest {
    @Test
    @DisplayName("성공-아파트 전월세 Json 데이터 가져오기")
    void ApartmentRent_Success_JsonData() {
        ResponseEntity<String> response;

        try {
            response = OpenApiExplorer.getResponse(
                    "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptRent?",
                    "11500",
                    "202304",
                    System.getenv("SEARCH_KEY")
            );
        } catch (IOException | JDOMException e) {
            throw new RuntimeException(e);
        }

        JsonNode root = OpenApiExplorer.transferStringToJson(response.getBody());
        JsonNode responseAttr = root.path("response");
        JsonNode body = responseAttr.path("body");
        JsonNode totalCount = body.path("totalCount");

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotEquals(0, totalCount.asInt());

    }

    @Test
    @DisplayName("실패-아파트 전월세 Json 데이터 가져오기-지역코드 없음")
    void ApartmentRent_Fail_WrongRegionCode_JsonData() {
        ResponseEntity<String> response;

        try {
            response = OpenApiExplorer.getResponse(
                    "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptRent?",
                    "wrongCode",
                    "202304",
                    System.getenv("SEARCH_KEY"));
        } catch (IOException | JDOMException e) {
            throw new RuntimeException(e);
        }

        JsonNode root = OpenApiExplorer.transferStringToJson(response.getBody());
        JsonNode responseAttr = root.path("response");
        JsonNode header = responseAttr.path("header");
        JsonNode totalCount = header.path("totalCount");

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(0, totalCount.asInt());
    }

    @Test
    @DisplayName("실패-아파트 전월세 Json 데이터 가져오기-searchKey 없음")
    void ApartmentRent_Fail_NoSearchKey_JsonData() {
        ResponseEntity<String> response;

        try {
            response = OpenApiExplorer.getResponse(
                    "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptRent?",
                    "wrongCode",
                    "202304",
                    null);
        } catch (IOException | JDOMException e) {
            throw new RuntimeException(e);
        }

        JsonNode root = OpenApiExplorer.transferStringToJson(response.getBody());
        JsonNode responseAttr = root.path("response");
        JsonNode header = responseAttr.path("header");
        JsonNode resultCode = header.path("resultCode");

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("99", resultCode.asText());
    }
}
