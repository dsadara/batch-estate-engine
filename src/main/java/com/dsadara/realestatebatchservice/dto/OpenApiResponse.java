package com.dsadara.realestatebatchservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
public class OpenApiResponse {

    @JsonProperty(value = "resultCode")
    private String resultCode;
    @JsonProperty(value = "resultMsg")
    private String resultMsg;
    @JsonProperty(value = "item")
    private List<AptRentDto> aptRentDtos;

    @SuppressWarnings("unchecked")
    @JsonProperty("response")
    private void unpackNestedResponse(Map<String, Object> response) {
        Map<String, Object> header = (Map<String, Object>) response.get("header");
        this.resultCode = (String) header.get("resultCode");
        this.resultMsg = (String) header.get("resultMsg");
        Map<String, Object> body = (Map<String, Object>) response.get("body");
        Map<String, Object> items = (Map<String, Object>) body.get("items");
        aptRentDtos = (List<AptRentDto>) items.get("item");
    }
}