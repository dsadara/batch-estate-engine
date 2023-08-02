package com.dsadara.realestatebatchservice.service;

import com.dsadara.realestatebatchservice.dto.RealEstateDataDto;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RequestData {

    private final JsonDeserializer jsonDeserializer;
    public RequestData(JsonDeserializer jsonDeserializer) {
        this.jsonDeserializer = jsonDeserializer;
    }

    public List<RealEstateDataDto> requestData(String baseURL, String legalDongCode, String contractYMD, String servicekey) throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("LAWD_CD", legalDongCode);
        queryParams.add("DEAL_YMD", contractYMD);
        queryParams.add("serviceKey", servicekey);

        ResponseEntity<String> response = jsonDeserializer.getResponse(baseURL, queryParams);
        Optional<JsonNode> itemOptional = jsonDeserializer.stringToJsonNode(response.getBody());
        return jsonDeserializer.jsonNodeToPOJO(itemOptional).orElse(new ArrayList<>());
    }
}
