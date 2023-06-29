package com.dsadara.realestatebatchservice.service;

import com.dsadara.realestatebatchservice.dto.OpenApiResponse;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;

@Service
public class RequestDataService {

    private final JsonDeserializerService jsonDeserializerService;
    private final RestTemplateService restTemplateService;

    public RequestDataService(JsonDeserializerService jsonDeserializerService, RestTemplateService restTemplateService) {
        this.jsonDeserializerService = jsonDeserializerService;
        this.restTemplateService = restTemplateService;
    }

    public OpenApiResponse requestData(String baseURL, String legalDongCode, String contractYMD, String servicekey) throws IOException {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("LAWD_CD", legalDongCode);
        queryParams.add("DEAL_YMD", contractYMD);
        queryParams.add("serviceKey", servicekey);

        ResponseEntity<String> response = restTemplateService.getResponse(baseURL, queryParams);
        JsonNode jsonNode = jsonDeserializerService.stringToJsonNode(response.getBody());
        return jsonDeserializerService.jsonNodeToPOJO(jsonNode);
    }
}
