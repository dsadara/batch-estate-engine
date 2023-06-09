package com.dsadara.realestatebatchservice.service;

import com.dsadara.realestatebatchservice.dto.OpenApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class JsonDeserializerService {
    private final ObjectMapper objectMapper;

    public JsonDeserializerService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public JsonNode stringToJsonNode(String rawJson) throws JsonProcessingException {
        return objectMapper.readTree(rawJson);
    }

    public OpenApiResponse jsonNodeToPOJO(JsonNode root) throws IOException {
        return objectMapper
                .readerFor(OpenApiResponse.class)
                .readValue(root);
    }
}
