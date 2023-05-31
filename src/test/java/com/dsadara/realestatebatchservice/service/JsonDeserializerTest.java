package com.dsadara.realestatebatchservice.service;

import com.dsadara.realestatebatchservice.dto.OpenApiResponse;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

@SpringBootTest
class JsonDeserializerTest {

    @Autowired
    private OpenApiExplorer openApiExplorer;
    @Autowired
    private JsonDeserializer jsonDeserializer;

    @Test
    void jsonNodeToPOJO_Success() throws IOException {
        ResponseEntity<String> response;

        response = openApiExplorer.getResponse(
                "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptRent?",
                "11500",
                "202304",
                "KNxUoxDnwzkyp3fb8dOjCWatfWm6VdGxJHzwOlvkSAcOcm%2B6%2BgIsOrcZ8Wr8hU0qzcmNE2tSjG7HUQBIA%2FqkYg%3D%3D"
        );

        JsonNode root = openApiExplorer.stringToJsonNode(response.getBody());
        OpenApiResponse openApiResponse = jsonDeserializer.jsonNodeToPOJO(root);

        Assertions.assertEquals("00", openApiResponse.getResultCode());
        Assertions.assertEquals("NORMAL SERVICE.", openApiResponse.getResultMsg());
        Assertions.assertNotEquals(0, openApiResponse.getAptRentDtos().size());
    }
}