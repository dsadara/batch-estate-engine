package com.dsadara.realestatebatchservice.service;

import com.dsadara.realestatebatchservice.dto.OpenApiResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class JsonDeserializer {
    public OpenApiResponse jsonNodeToPOJO(JsonNode root) throws IOException {
        return new ObjectMapper()
                .readerFor(OpenApiResponse.class)
                .readValue(root);
    }
}
