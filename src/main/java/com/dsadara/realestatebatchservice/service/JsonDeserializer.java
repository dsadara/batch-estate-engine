package com.dsadara.realestatebatchservice.service;

import com.dsadara.realestatebatchservice.dto.OpenApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@Service
public class JsonDeserializer {
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    public JsonDeserializer(ObjectMapper objectMapper, RestTemplate restTemplate) {
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
    }

    public JsonNode stringToJsonNode(String rawJson) throws JsonProcessingException {
        return objectMapper.readTree(rawJson);
    }

    public OpenApiResponse jsonNodeToPOJO(JsonNode root) throws IOException {
        return objectMapper
                .readerFor(OpenApiResponse.class)
                .readValue(root);
    }

    public ResponseEntity<String> getResponse(String baseUrl, MultiValueMap<String, String> queryParams) {
        URI uri = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParams(queryParams)
                .build(true)
                .toUri();

        return restTemplate.getForEntity(uri, String.class);
    }
}
