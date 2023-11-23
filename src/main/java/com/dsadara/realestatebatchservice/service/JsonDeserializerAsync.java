package com.dsadara.realestatebatchservice.service;

import com.dsadara.realestatebatchservice.dto.RealEstateDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class JsonDeserializerAsync {

    private final AsyncRestTemplate asyncRestTemplate;
    private final ObjectMapper objectMapper;

    public JsonDeserializerAsync(AsyncRestTemplate asyncRestTemplate, ObjectMapper objectMapper) {
        this.asyncRestTemplate = asyncRestTemplate;
        this.objectMapper = objectMapper;
    }

    public Optional<JsonNode> stringToJsonNode(String rawJson) {
        try {
            return Optional.ofNullable(
                    objectMapper.readTree(rawJson)
                            .path("response")
                            .path("body")
                            .path("items")
                            .findValue("item"));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<List<RealEstateDto>> jsonNodeToPOJO(Optional<JsonNode> itemOptional) {
        try {
            return Optional.ofNullable(
                    objectMapper.readerFor(new TypeReference<List<RealEstateDto>>() {
                            })
                            .readValue(itemOptional.orElse(objectMapper.nullNode())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public CompletableFuture<ResponseEntity<String>> getResponse(String baseUrl, MultiValueMap<String, String> queryParams) {
        URI uri = UriComponentsBuilder
                .fromUriString(baseUrl)
                .queryParams(queryParams)
                .build(true)
                .toUri();
        return toCFuture(asyncRestTemplate.getForEntity(uri, String.class));
    }

    private <T> CompletableFuture<T> toCFuture(ListenableFuture<T> lf) {
        CompletableFuture<T> cf = new CompletableFuture<>();
        lf.addCallback((r) -> {
            cf.complete(r);
        }, (e) -> {
            cf.completeExceptionally(e);
        });
        return cf;
    }

}
