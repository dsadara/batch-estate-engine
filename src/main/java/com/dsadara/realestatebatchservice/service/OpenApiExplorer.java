package com.dsadara.realestatebatchservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jdom2.JDOMException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
public class OpenApiExplorer {
    public static ResponseEntity<String> getResponse(String baseUrl, String regionCode, String contractYearMonthDay, String authenticationKey) throws IOException, JDOMException {
        URI uri;

        try {
            uri = new URI(baseUrl +
                    "LAWD_CD=" + regionCode + "&" +
                    "DEAL_YMD=" + contractYearMonthDay + "&" +
                    "serviceKey=" + authenticationKey + "&" +
                    "_type=json");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForEntity(uri, String.class);
    }

    public static JsonNode transferStringToJson(String rawJson) {
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
