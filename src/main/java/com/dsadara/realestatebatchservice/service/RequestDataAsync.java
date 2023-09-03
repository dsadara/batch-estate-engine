package com.dsadara.realestatebatchservice.service;

import com.dsadara.realestatebatchservice.dto.RealEstateDto;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RequestDataAsync {
    private final GenerateApiQueryParam generateApiQueryParam;
    private final JsonDeserializerAsync jsonDeserializerAsync;

    public RequestDataAsync(GenerateApiQueryParam generateApiQueryParam, JsonDeserializerAsync jsonDeserializerAsync) {
        this.generateApiQueryParam = generateApiQueryParam;
        this.jsonDeserializerAsync = jsonDeserializerAsync;
    }

    public List<RealEstateDto> requestDataOneBjd(String baseURL, String legalDongCode, String servicekey) throws ExecutionException, InterruptedException {
        List<String> dealYearMonthsList = generateApiQueryParam.getDealYearMonthsList();
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        List<CompletableFuture<List<RealEstateDto>>> completableFutureList = new ArrayList<>();

        for (String dealYearMonth : dealYearMonthsList) {
            queryParams.clear();
            queryParams.add("LAWD_CD", legalDongCode);
            queryParams.add("DEAL_YMD", dealYearMonth);
            queryParams.add("serviceKey", servicekey);

            completableFutureList.add(
                    jsonDeserializerAsync.getResponse(baseURL, queryParams)
                            .thenApply(s -> {
                                Optional<JsonNode> optionalJsonNode = jsonDeserializerAsync.stringToJsonNode(s.getBody());
                                return jsonDeserializerAsync.jsonNodeToPOJO(optionalJsonNode).orElse(new ArrayList<>());
                            }));
        }

        List<List<RealEstateDto>> realEstateDtosList
                = CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[completableFutureList.size()]))
                .thenApply(v -> completableFutureList.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList())).get();

        List<RealEstateDto> result = new LinkedList<>();
        result.add(new RealEstateDto());
        realEstateDtosList.forEach(result::addAll);
        return result;
    }
}
