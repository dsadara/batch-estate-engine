package com.dsadara.realestatebatchservice;

import org.jdom2.JDOMException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
public class ApiExplorerTest {
    @Test
    void ApiExplorerTest_ApartmentRent_Success() {
        List<AptRentXMLObject> objects;

        try {
            objects = ApiExplorer.apiCall(
                    "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptRent?",
                    "11500",
                    "202304",
                    System.getenv("SEARCH_KEY")
            );
        } catch (IOException | JDOMException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals("염창동", objects.get(0).getLegalDong());
        Assertions.assertEquals("무학", objects.get(0).getName());

        Assertions.assertEquals("염창동", objects.get(1).getLegalDong());
        Assertions.assertEquals("일신건영휴먼빌", objects.get(1).getName());
    }

    @Test
    void ApiExplorerTest_ApartmentRent_Fail() {
        List<AptRentXMLObject> objects;

        try {
            objects = ApiExplorer.apiCall(
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
