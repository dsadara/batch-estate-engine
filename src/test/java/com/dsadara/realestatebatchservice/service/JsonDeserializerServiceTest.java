package com.dsadara.realestatebatchservice.service;

import com.dsadara.realestatebatchservice.dto.AptRentDto;
import com.dsadara.realestatebatchservice.dto.OpenApiResponse;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;

@SpringBootTest
class JsonDeserializerServiceTest {

    @Autowired
    private RestTemplateService restTemplateService;
    @Autowired
    private JsonDeserializerService jsonDeserializerService;

    @Test
    void jsonNodeToPOJO_Success() throws IOException {
        ResponseEntity<String> response;

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("LAWD_CD", "11500");
        queryParams.add("DEAL_YMD", "202304");
        queryParams.add("serviceKey", "KNxUoxDnwzkyp3fb8dOjCWatfWm6VdGxJHzwOlvkSAcOcm%2B6%2BgIsOrcZ8Wr8hU0qzcmNE2tSjG7HUQBIA%2FqkYg%3D%3D");

        response = restTemplateService.getResponse(
                "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptRent?",
                queryParams
        );

        JsonNode root = jsonDeserializerService.stringToJsonNode(response.getBody());
        OpenApiResponse openApiResponse = jsonDeserializerService.jsonNodeToPOJO(root);

        Assertions.assertEquals("00", openApiResponse.getResultCode());
        Assertions.assertEquals("NORMAL SERVICE.", openApiResponse.getResultMsg());
        Assertions.assertNotEquals(0, openApiResponse.getAptRentDtos().size());
        Assertions.assertEquals(AptRentDto.class, openApiResponse.getAptRentDtos().get(0).getClass());
        Assertions.assertEquals("염창동", openApiResponse.getAptRentDtos().get(0).getLegalDong());
    }
}