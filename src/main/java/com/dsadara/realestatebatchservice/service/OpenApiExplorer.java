package com.dsadara.realestatebatchservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class OpenApiExplorer {
    private final RestTemplate restTemplate;

    public OpenApiExplorer(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> getResponse(String baseUrl, String regionCode, String contractYearMonthDay, String authenticationKey) {
        Map<String, String> params = new HashMap<>();
        params.put("regionCode", regionCode);
        params.put("contractYearMonthDay", contractYearMonthDay);
        params.put("authenticationKey", authenticationKey);

        return restTemplate.getForEntity(
                baseUrl + "LAWD_CD={regionCode}&DEAL_YMD={contractYearMonthDay}&serviceKey={authenticationKey}",
                String.class,
                params);
    }

    public JsonNode transferStringToJson(String rawJson) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root;

        try {
            root = mapper.readTree(rawJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return root;
    }
}
