package com.dsadara.realestatebatchservice.service;

import com.dsadara.realestatebatchservice.dto.RealEstateDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ApiRequesterMockTest {

    @Mock
    private RealEstateDataFetcher realEstateDataFetcher;
    @InjectMocks
    private ApiRequester apiRequester;

    // 아파트 전월세 요청 파라미터, 비교용 데이터
    private Optional<JsonNode> jsonNodeOptional;
    private Optional<List<RealEstateDto>> realEstateDataDtos;
    private ResponseEntity<String> response;
    private String baseURL;
    private String legalDong;
    private String dealYearMonth;
    private String searchKey;

    @BeforeEach
    void setUp() throws Exception {
        prepareDataForRequestAptRent();
    }

    void prepareDataForRequestAptRent() throws Exception {
        RealEstateDataFetcher realEstateDataFetcherTemp = new RealEstateDataFetcher(new ObjectMapper(), new RestTemplate());

        legalDong = "11500";
        dealYearMonth = "202304";
        searchKey = "KNxUoxDnwzkyp3fb8dOjCWatfWm6VdGxJHzwOlvkSAcOcm%2B6%2BgIsOrcZ8Wr8hU0qzcmNE2tSjG7HUQBIA%2FqkYg%3D%3D";
        baseURL = "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptRent?";

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("LAWD_CD", legalDong);
        queryParams.add("DEAL_YMD", dealYearMonth);
        queryParams.add("serviceKey", searchKey);

        response = realEstateDataFetcherTemp.getResponse(baseURL, queryParams);
        jsonNodeOptional = realEstateDataFetcherTemp.stringToJsonNode(response.getBody());
        realEstateDataDtos = realEstateDataFetcherTemp.jsonNodeToPOJO(jsonNodeOptional);
    }

    @Test
    @DisplayName("성공-requestData()")
    void requestData_Success() throws Exception {
        //when
        when(realEstateDataFetcher.getResponse(anyString(), ArgumentMatchers.<MultiValueMap<String, String>>any())).thenReturn(response);
        when(realEstateDataFetcher.stringToJsonNode(anyString())).thenReturn(jsonNodeOptional);
        when(realEstateDataFetcher.jsonNodeToPOJO(jsonNodeOptional)).thenReturn(realEstateDataDtos);

        //then
        Assertions.assertEquals(realEstateDataDtos.get(),
                apiRequester.fetchData(baseURL, legalDong, dealYearMonth, searchKey));
        verify(realEstateDataFetcher, times(1)).getResponse(anyString(), ArgumentMatchers.<MultiValueMap<String, String>>any());
        verify(realEstateDataFetcher, times(1)).stringToJsonNode(anyString());
        verify(realEstateDataFetcher, times(1)).jsonNodeToPOJO(jsonNodeOptional);
    }

    @Test
    @DisplayName("실패-requestData()-IOException")
    void requestData_Failure_IOException() throws Exception {
        //when
        when(realEstateDataFetcher.getResponse(anyString(), ArgumentMatchers.<MultiValueMap<String, String>>any())).thenReturn(response);
        when(realEstateDataFetcher.stringToJsonNode(anyString())).thenReturn(jsonNodeOptional);
        when(realEstateDataFetcher.jsonNodeToPOJO(jsonNodeOptional)).thenThrow(IOException.class);

        //then
        Assertions.assertThrows(IOException.class,
                () -> apiRequester.fetchData(baseURL, legalDong, dealYearMonth, searchKey));
        verify(realEstateDataFetcher, times(1)).getResponse(anyString(), ArgumentMatchers.<MultiValueMap<String, String>>any());
        verify(realEstateDataFetcher, times(1)).stringToJsonNode(anyString());
    }

    @Test
    @DisplayName("실패-requestData()-JsonProcessionException")
    void requestAptRent_Failure_JsonProcessionException() throws Exception {
        //when
        when(realEstateDataFetcher.getResponse(anyString(), ArgumentMatchers.<MultiValueMap<String, String>>any())).thenReturn(response);
        when(realEstateDataFetcher.stringToJsonNode(anyString())).thenReturn(jsonNodeOptional);
        when(realEstateDataFetcher.jsonNodeToPOJO(jsonNodeOptional)).thenThrow(JsonProcessingException.class);

        //then
        Assertions.assertThrows(JsonProcessingException.class,
                () -> apiRequester.fetchData(baseURL, legalDong, dealYearMonth, searchKey));
        verify(realEstateDataFetcher, times(1)).getResponse(anyString(), ArgumentMatchers.<MultiValueMap<String, String>>any());
    }

}