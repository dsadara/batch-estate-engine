package com.dsadara.realestatebatchservice.service;

import com.dsadara.realestatebatchservice.dto.OpenApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AptRentServiceUnitTest {

    @Mock
    private RestTemplateService restTemplateService;
    @Mock
    private JsonDeserializerService jsonDeserializerService;

    @InjectMocks
    private AptRentService aptRentService;

    // requestAptRent() Data
    private static JsonNode jsonNodeSample;
    private static OpenApiResponse openApiResponseSample;
    private static ResponseEntity<String> responseSample;
    private static String legalDong;
    private static String contractYMD;
    private static String searchKey;

    @BeforeAll
    static void beforeAll() throws IOException {
        prepareDataForRequestAptRent();
    }

    static void prepareDataForRequestAptRent() throws IOException {
        RestTemplateService restTemplateServiceTemp = new RestTemplateService(new RestTemplate());
        JsonDeserializerService jsonDeserializerServiceTemp = new JsonDeserializerService(new ObjectMapper());

        legalDong = "11500";
        contractYMD = "202304";
        searchKey = "KNxUoxDnwzkyp3fb8dOjCWatfWm6VdGxJHzwOlvkSAcOcm%2B6%2BgIsOrcZ8Wr8hU0qzcmNE2tSjG7HUQBIA%2FqkYg%3D%3D";

        String baseUrl = "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptRent?";
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("LAWD_CD", legalDong);
        queryParams.add("DEAL_YMD", contractYMD);
        queryParams.add("serviceKey", searchKey);

        responseSample = restTemplateServiceTemp.getResponse(baseUrl, queryParams);
        jsonNodeSample = jsonDeserializerServiceTemp.stringToJsonNode(responseSample.getBody());
        openApiResponseSample = jsonDeserializerServiceTemp.jsonNodeToPOJO(jsonNodeSample);
    }

    @Test
    @DisplayName("성공-requestAptRent")
    void requestAptRent_Success() throws IOException {
        //given
        //when
        when(restTemplateService.getResponse(anyString(), ArgumentMatchers.<MultiValueMap<String, String>>any())).thenReturn(responseSample);
        when(jsonDeserializerService.stringToJsonNode(anyString())).thenReturn(jsonNodeSample);
        when(jsonDeserializerService.jsonNodeToPOJO(any(JsonNode.class))).thenReturn(openApiResponseSample);
        //then
        Assertions.assertEquals(openApiResponseSample, aptRentService.requestAptRent(legalDong, contractYMD, searchKey));
        verify(restTemplateService, times(1)).getResponse(anyString(), ArgumentMatchers.<MultiValueMap<String, String>>any());
        verify(jsonDeserializerService, times(1)).stringToJsonNode(anyString());
        verify(jsonDeserializerService, times(1)).jsonNodeToPOJO(any(JsonNode.class));
    }

    @Test
    @DisplayName("실패-requestAptRent-IOException")
    void requestAptRent_Failure_IOException() throws IOException {
        //given
        //when
        when(restTemplateService.getResponse(anyString(), ArgumentMatchers.<MultiValueMap<String, String>>any())).thenReturn(responseSample);
        when(jsonDeserializerService.stringToJsonNode(anyString())).thenReturn(jsonNodeSample);
        when(jsonDeserializerService.jsonNodeToPOJO(any(JsonNode.class))).thenThrow(IOException.class);
        //then
        Assertions.assertThrows(IOException.class, () -> aptRentService.requestAptRent(legalDong, contractYMD, searchKey));
        verify(restTemplateService, times(1)).getResponse(anyString(), ArgumentMatchers.<MultiValueMap<String, String>>any());
        verify(jsonDeserializerService, times(1)).stringToJsonNode(anyString());
    }

    @Test
    @DisplayName("실패-requestAptRent-JsonProcessionException")
    void requestAptRent_Failure_JsonProcessionException() throws IOException {
        //given
        //when
        when(restTemplateService.getResponse(anyString(), ArgumentMatchers.<MultiValueMap<String, String>>any())).thenReturn(responseSample);
        when(jsonDeserializerService.stringToJsonNode(anyString())).thenReturn(jsonNodeSample);
        when(jsonDeserializerService.jsonNodeToPOJO(any(JsonNode.class))).thenThrow(JsonProcessingException.class);
        //then
        Assertions.assertThrows(JsonProcessingException.class, () -> aptRentService.requestAptRent(legalDong, contractYMD, searchKey));
        verify(restTemplateService, times(1)).getResponse(anyString(), ArgumentMatchers.<MultiValueMap<String, String>>any());
    }
}