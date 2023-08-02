package com.dsadara.realestatebatchservice.service;

import com.dsadara.realestatebatchservice.dto.RealEstateDataDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
public class JsonDeserializer {
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    public JsonDeserializer(ObjectMapper objectMapper, RestTemplate restTemplate) {
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
    }

    public JsonNode stringToJsonNode(String rawJson) throws Exception {
        return objectMapper.readTree(rawJson)
                .path("response")
                .path("body")
                .path("items")
                .findValue("item");
    }

    public List<RealEstateDataDto> jsonNodeToPOJO(JsonNode item) throws Exception {
        return objectMapper
                .readerFor(new TypeReference<List<RealEstateDataDto>>() {
                })
                .readValue(item);
    }

    public ResponseEntity<String> getResponse(String baseUrl, MultiValueMap<String, String> queryParams) {
        URI uri = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParams(queryParams)
                .build(true)
                .toUri();

        return restTemplate.getForEntity(uri, String.class);
    }
}
