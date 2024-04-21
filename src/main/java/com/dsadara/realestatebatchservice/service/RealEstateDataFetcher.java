package com.dsadara.realestatebatchservice.service;

import com.dsadara.realestatebatchservice.dto.RealEstateDto;
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
import java.util.Optional;

@Service
public class RealEstateDataFetcher {

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    public RealEstateDataFetcher(ObjectMapper objectMapper, RestTemplate restTemplate) {
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
    }

    public Optional<JsonNode> stringToJsonNode(String rawJson) throws Exception {
        return Optional.ofNullable(
                objectMapper.readTree(rawJson)
                        .path("response")
                        .path("body")
                        .path("items")
                        .findValue("item")
        );
    }

    public Optional<List<RealEstateDto>> jsonNodeToPOJO(Optional<JsonNode> itemOptional) throws Exception {
        return Optional.ofNullable(
                objectMapper.readerFor(new TypeReference<List<RealEstateDto>>() {
                        })
                        .readValue(itemOptional.orElse(objectMapper.nullNode()))
        );
    }

    public ResponseEntity<String> getResponse(String baseUrl, MultiValueMap<String, String> queryParams) {
        URI uri = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParams(queryParams)
                .build(true)
                .toUri();
        return restTemplate.getForEntity(uri, String.class);
    }

}
