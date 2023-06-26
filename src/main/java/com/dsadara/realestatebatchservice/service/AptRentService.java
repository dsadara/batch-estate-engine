package com.dsadara.realestatebatchservice.service;

import com.dsadara.realestatebatchservice.dto.OpenApiResponse;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;

@Service
public class AptRentService {
    private final JsonDeserializerService jsonDeserializerService;
    private final RestTemplateService restTemplateService;

    public AptRentService(JsonDeserializerService jsonDeserializerService, RestTemplateService restTemplateService) {
        this.jsonDeserializerService = jsonDeserializerService;
        this.restTemplateService = restTemplateService;
    }

    public OpenApiResponse requestAptRent(String legalDongCode, String contractYMD, String servicekey) throws IOException {
        String baseUrl = "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptRent?";
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("LAWD_CD", legalDongCode);
        queryParams.add("DEAL_YMD", contractYMD);
        queryParams.add("serviceKey", servicekey);

        ResponseEntity<String> response = restTemplateService.getResponse(baseUrl, queryParams);
        JsonNode jsonNode = jsonDeserializerService.stringToJsonNode(response.getBody());
        return jsonDeserializerService.jsonNodeToPOJO(jsonNode);
    }
}
