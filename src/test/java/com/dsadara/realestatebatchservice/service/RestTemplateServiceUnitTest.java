package com.dsadara.realestatebatchservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.web.client.HttpClientErrorException.NotFound;
import static org.springframework.web.client.HttpServerErrorException.InternalServerError;

@ExtendWith(MockitoExtension.class)
public class RestTemplateServiceUnitTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RestTemplateService restTemplateService;

    private static String baseUrl;
    private static String wrongBaseUrl;
    private static MultiValueMap<String, String> queryParams;
    private static ResponseEntity<String> responseSample;

    @BeforeAll
    static void beforeAll() {
        RestTemplateService restTemplateService = new RestTemplateService(new RestTemplate());
        queryParams = new LinkedMultiValueMap<>();
        queryParams.add("LAWD_CD", "11500");
        queryParams.add("DEAL_YMD", "202304");
        queryParams.add("serviceKey", "KNxUoxDnwzkyp3fb8dOjCWatfWm6VdGxJHzwOlvkSAcOcm%2B6%2BgIsOrcZ8Wr8hU0qzcmNE2tSjG7HUQBIA%2FqkYg%3D%3D");
        baseUrl = "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptRent?";
        wrongBaseUrl = "http://openapi.molit.go.kr:8081/WrongURI/service/rest/RTMSOBJSvc/getRTMSDataSvcAptRent?";
        responseSample = restTemplateService.getResponse(baseUrl, queryParams);
    }

    @Test
    @DisplayName("성공-getResponse()")
    void getResponse_Success() {
        //given
        //when
        when(restTemplate.getForEntity(any(URI.class), eq(String.class))).thenReturn(responseSample);
        //then
        Assertions.assertEquals(responseSample, restTemplateService.getResponse(baseUrl, queryParams));
        verify(restTemplate, times(1)).getForEntity(any(URI.class), eq(String.class));
    }

    @Test
    @DisplayName("실패-getResponse()-404 Not Found")
    void getResponse_Failure_404NotFound() {
        //given
        //when
        when(restTemplate.getForEntity(any(URI.class), eq(String.class))).thenThrow(NotFound.class);
        //then
        Assertions.assertThrows(NotFound.class, () -> restTemplateService.getResponse(wrongBaseUrl, queryParams));
        verify(restTemplate, times(1)).getForEntity(any(URI.class), eq(String.class));
    }

    @Test
    @DisplayName("실패-getResponse()-Internal Server Error")
    void getResponse_Failure_InternalServerError() {
        //given
        //when
        when(restTemplate.getForEntity(any(URI.class), eq(String.class))).thenThrow(InternalServerError.class);
        //then
        Assertions.assertThrows(InternalServerError.class, () -> restTemplateService.getResponse(wrongBaseUrl, queryParams));
        verify(restTemplate, times(1)).getForEntity(any(URI.class), eq(String.class));
    }

}
