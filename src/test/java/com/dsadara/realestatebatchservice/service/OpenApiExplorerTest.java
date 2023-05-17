package com.dsadara.realestatebatchservice.service;

import com.dsadara.realestatebatchservice.dto.AptRentDto;
import org.jdom2.JDOMException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
public class OpenApiExplorerTest {
    @Test
    void ApiExplorerTest_ApartmentRent_Success() {
        List<AptRentDto> objects;

        try {
            objects = OpenApiExplorer.getAptRentData(
                    "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptRent?",
                    "11500",
                    "202304",
                    System.getenv("SEARCH_KEY")
            );
        } catch (IOException | JDOMException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertNotEquals(0, objects.size());
        Assertions.assertEquals(1112, objects.size());  // 과거 데이터 개수는 변경될 수 있음

    }

    @Test
    void ApiExplorerTest_ApartmentRent_Fail() {
        List<AptRentDto> objects;

        try {
            objects = OpenApiExplorer.getAptRentData(
                    "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptRent?",
                    "wrongCode",
                    "WrongYearMonthDay",
                    System.getenv("SEARCH_KEY"));
        } catch (IOException | JDOMException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals(objects.size(), 0);
    }
}
