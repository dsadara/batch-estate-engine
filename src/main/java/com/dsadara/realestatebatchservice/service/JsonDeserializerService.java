package com.dsadara.realestatebatchservice.service;

import com.dsadara.realestatebatchservice.dto.OpenApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class JsonDeserializerService {
    public JsonNode stringToJsonNode(String rawJson) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root;

        try {
            root = mapper.readTree(rawJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return root;
    }

    public OpenApiResponse jsonNodeToPOJO(JsonNode root) throws IOException {
        return new ObjectMapper()
                .readerFor(OpenApiResponse.class)
                .readValue(root);
    }
}
