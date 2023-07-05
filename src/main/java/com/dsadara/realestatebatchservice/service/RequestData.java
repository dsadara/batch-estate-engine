package com.dsadara.realestatebatchservice.service;

import com.dsadara.realestatebatchservice.dto.OpenApiResponse;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;

@Service
public class RequestData {

    private final JsonDeserializer jsonDeserializer;
    private final RestTemplates restTemplates;

    public RequestData(JsonDeserializer jsonDeserializer, RestTemplates restTemplates) {
        this.jsonDeserializer = jsonDeserializer;
        this.restTemplates = restTemplates;
    }

    public OpenApiResponse requestData(String baseURL, String legalDongCode, String contractYMD, String servicekey) throws IOException {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("LAWD_CD", legalDongCode);
        queryParams.add("DEAL_YMD", contractYMD);
        queryParams.add("serviceKey", servicekey);

        ResponseEntity<String> response = restTemplates.getResponse(baseURL, queryParams);
        JsonNode jsonNode = jsonDeserializer.stringToJsonNode(response.getBody());
        return jsonDeserializer.jsonNodeToPOJO(jsonNode);
    }
}
