package com.dsadara.realestatebatchservice.service;

import com.dsadara.realestatebatchservice.dto.RealEstateDto;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RequestData {

    private final JsonDeserializer jsonDeserializer;

    public RequestData(JsonDeserializer jsonDeserializer) {
        this.jsonDeserializer = jsonDeserializer;
    }

    public List<RealEstateDto> requestData(String baseURL, String servicekey, String bjdCode, String contractYMD) throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("LAWD_CD", bjdCode);
        queryParams.add("DEAL_YMD", contractYMD);
        queryParams.add("serviceKey", servicekey);

        ResponseEntity<String> response = jsonDeserializer.getResponse(baseURL, queryParams);
        Optional<JsonNode> itemOptional = jsonDeserializer.stringToJsonNode(response.getBody());
        List<RealEstateDto> realEstateDtos = jsonDeserializer.jsonNodeToPOJO(itemOptional).orElse(new ArrayList<>());
        log.info("[법정동 코드 {}][계약 연월일 {}] api 호출 완료 -> ( 데이터 개수: {} )", bjdCode, contractYMD, realEstateDtos.size());

        return realEstateDtos;
    }
}
