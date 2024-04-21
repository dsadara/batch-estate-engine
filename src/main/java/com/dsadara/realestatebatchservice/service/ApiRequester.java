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
public class ApiRequester {

    private final RealEstateDataFetcher realEstateDataFetcher;

    public ApiRequester(RealEstateDataFetcher realEstateDataFetcher) {
        this.realEstateDataFetcher = realEstateDataFetcher;
    }

    public List<RealEstateDto> fetchData(String baseURL, String servicekey, String bjdCode, String dealYearMonth) throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("LAWD_CD", bjdCode);
        queryParams.add("DEAL_YMD", dealYearMonth);
        queryParams.add("serviceKey", servicekey);

        ResponseEntity<String> response = realEstateDataFetcher.getResponse(baseURL, queryParams);
        Optional<JsonNode> itemOptional = realEstateDataFetcher.stringToJsonNode(response.getBody());
        List<RealEstateDto> realEstateDtos = realEstateDataFetcher.jsonNodeToPOJO(itemOptional).orElse(new ArrayList<>());
        log.info("[법정동 코드 {}][계약 연월일 {}] api 호출 완료 -> ( 데이터 개수: {} )", bjdCode, dealYearMonth, realEstateDtos.size());

        return realEstateDtos;
    }

}
