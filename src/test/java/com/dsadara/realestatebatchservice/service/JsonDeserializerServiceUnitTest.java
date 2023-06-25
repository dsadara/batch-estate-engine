package com.dsadara.realestatebatchservice.service;

import com.dsadara.realestatebatchservice.dto.OpenApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JsonDeserializerServiceUnitTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private ObjectMapper objectMapper;

    @InjectMocks
    private JsonDeserializerService jsonDeserializerService;

    private static String rawJsonSample;
    private static String rawJsonSampleInvalid;
    private static JsonNode jsonNodeSample;
    private static JsonNode jsonNodeSampleInvalid;
    private static OpenApiResponse openApiResponseSample;

    @BeforeAll
    static void beforeAll() throws IOException {
        JsonDeserializerService jsonDeserializerService = new JsonDeserializerService(new ObjectMapper());

        rawJsonSample = "{\"response\":{\"header\":{\"resultCode\":\"00\",\"resultMsg\":\"NORMAL SERVICE.\"},\"body\":{\"items\":{\"item\":[{\"갱신요구권사용\":\" \",\"건축년도\":2019,\"계약구분\":\"신규\",\"계약기간\":\"23.05~25.05\",\"년\":2023,\"법정동\":\"염창동\",\"보증금액\":\"55,000\",\"아파트\":\"e편한세상염창\",\"월\":4,\"월세금액\":0,\"일\":1,\"전용면적\":59.8873,\"종전계약보증금\":\" \",\"종전계약월세\":\" \",\"지번\":309,\"지역코드\":11500,\"층\":16},{\"갱신요구권사용\":\" \",\"건축년도\":1998,\"계약구분\":\"신규\",\"계약기간\":\"23.05~25.05\",\"년\":2023,\"법정동\":\"염창동\",\"보증금액\":\"34,000\",\"아파트\":\"동아\",\"월\":4,\"월세금액\":0,\"일\":1,\"전용면적\":59.97,\"종전계약보증금\":\" \",\"종전계약월세\":\" \",\"지번\":292,\"지역코드\":11500,\"층\":9},{\"갱신요구권사용\":\" \",\"건축년도\":2019,\"계약구분\":\"신규\",\"계약기간\":\"23.06~25.06\",\"년\":2023,\"법정동\":\"염창동\",\"보증금액\":\"65,000\",\"아파트\":\"e편한세상염창\",\"월\":4,\"월세금액\":0,\"일\":1,\"전용면적\":84.9529,\"종전계약보증금\":\" \",\"종전계약월세\":\" \",\"지번\":309,\"지역코드\":11500,\"층\":1},{\"갱신요구권사용\":\" \",\"건축년도\":2021,\"계약구분\":\" \",\"계약기간\":\" \",\"년\":2023,\"법정동\":\"염창동\",\"보증금액\":\"35,000\",\"아파트\":\"등촌제이스카이\",\"월\":4,\"월세금액\":0,\"일\":1,\"전용면적\":39.87,\"종전계약보증금\":\" \",\"종전계약월세\":\" \",\"지번\":311,\"지역코드\":11500,\"층\":2},{\"갱신요구권사용\":\" \",\"건축년도\":1998,\"계약구분\":\"신규\",\"계약기간\":\"23.05~25.05\",\"년\":2023,\"법정동\":\"염창동\",\"보증금액\":\"28,000\",\"아파트\":\"동아\",\"월\":4,\"월세금액\":0,\"일\":1,\"전용면적\":59.97,\"종전계약보증금\":\" \",\"종전계약월세\":\" \",\"지번\":292,\"지역코드\":11500,\"층\":11},{\"갱신요구권사용\":\" \",\"건축년도\":1994,\"계약구분\":\" \",\"계약기간\":\"23.05~25.08\",\"년\":2023,\"법정동\":\"방화동\",\"보증금액\":\"24,500\",\"아파트\":\"장미\",\"월\":4,\"월세금액\":0,\"일\":29,\"전용면적\":39.96,\"종전계약보증금\":\" \",\"종전계약월세\":\" \",\"지번\":841,\"지역코드\":11500,\"층\":9}]},\"numOfRows\":10,\"pageNo\":1,\"totalCount\":1245}}}";
        rawJsonSampleInvalid = "\"response\":{\"header\":{\"resultCode\":\"00\",\"resultMsg\":\"NORMAL SERVICE.\"},\"body\":{\"items\":{\"item\":[{\"갱신요구권사용\":\" \",\"건축년도\":2019,\"계약구분\":\"신규\",\"계약기간\":\"23.05~25.05\",\"년\":2023,\"법정동\":\"염창동\",\"보증금액\":\"55,000\",\"아파트\":\"e편한세상염창\",\"월\":4,\"월세금액\":0,\"일\":1,\"전용면적\":59.8873,\"종전계약보증금\":\" \",\"종전계약월세\":\" \",\"지번\":309,\"지역코드\":11500,\"층\":16},{\"갱신요구권사용\":\" \",\"건축년도\":1998,\"계약구분\":\"신규\",\"계약기간\":\"23.05~25.05\",\"년\":2023,\"법정동\":\"염창동\",\"보증금액\":\"34,000\",\"아파트\":\"동아\",\"월\":4,\"월세금액\":0,\"일\":1,\"전용면적\":59.97,\"종전계약보증금\":\" \",\"종전계약월세\":\" \",\"지번\":292,\"지역코드\":11500,\"층\":9},{\"갱신요구권사용\":\" \",\"건축년도\":2019,\"계약구분\":\"신규\",\"계약기간\":\"23.06~25.06\",\"년\":2023,\"법정동\":\"염창동\",\"보증금액\":\"65,000\",\"아파트\":\"e편한세상염창\",\"월\":4,\"월세금액\":0,\"일\":1,\"전용면적\":84.9529,\"종전계약보증금\":\" \",\"종전계약월세\":\" \",\"지번\":309,\"지역코드\":11500,\"층\":1},{\"갱신요구권사용\":\" \",\"건축년도\":2021,\"계약구분\":\" \",\"계약기간\":\" \",\"년\":2023,\"법정동\":\"염창동\",\"보증금액\":\"35,000\",\"아파트\":\"등촌제이스카이\",\"월\":4,\"월세금액\":0,\"일\":1,\"전용면적\":39.87,\"종전계약보증금\":\" \",\"종전계약월세\":\" \",\"지번\":311,\"지역코드\":11500,\"층\":2},{\"갱신요구권사용\":\" \",\"건축년도\":1998,\"계약구분\":\"신규\",\"계약기간\":\"23.05~25.05\",\"년\":2023,\"법정동\":\"염창동\",\"보증금액\":\"28,000\",\"아파트\":\"동아\",\"월\":4,\"월세금액\":0,\"일\":1,\"전용면적\":59.97,\"종전계약보증금\":\" \",\"종전계약월세\":\" \",\"지번\":292,\"지역코드\":11500,\"층\":11},{\"갱신요구권사용\":\" \",\"건축년도\":1994,\"계약구분\":\" \",\"계약기간\":\"23.05~25.08\",\"년\":2023,\"법정동\":\"방화동\",\"보증금액\":\"24,500\",\"아파트\":\"장미\",\"월\":4,\"월세금액\":0,\"일\":29,\"전용면적\":39.96,\"종전계약보증금\":\" \",\"종전계약월세\":\" \",\"지번\":841,\"지역코드\":11500,\"층\":9}]},\"numOfRows\":10,\"pageNo\":1,\"totalCount\":1245}}}";
        jsonNodeSample = jsonDeserializerService.stringToJsonNode(rawJsonSample);
        jsonNodeSampleInvalid = jsonDeserializerService.stringToJsonNode(rawJsonSampleInvalid);
        openApiResponseSample = jsonDeserializerService.jsonNodeToPOJO(jsonNodeSample);
    }

    @Test
    @DisplayName("성공-stringToJsonNode()")
    void stringToJsonNode_Success() throws JsonProcessingException {
        //given
        //when
        when(objectMapper.readTree(rawJsonSample)).thenReturn(jsonNodeSample);
        //then
        assertEquals(jsonNodeSample, jsonDeserializerService.stringToJsonNode(rawJsonSample));
        verify(objectMapper, times(1)).readTree(anyString());
    }

    @Test
    @DisplayName("실패-stringToJsonNode()-부적절한json")
    void stringToJsonNode_InvalidJson() throws JsonProcessingException {
        //given
        //when
        when(objectMapper.readTree(rawJsonSampleInvalid)).thenReturn(jsonNodeSampleInvalid);
        //then
        assertEquals(jsonNodeSampleInvalid, jsonDeserializerService.stringToJsonNode(rawJsonSampleInvalid));
        verify(objectMapper, times(1)).readTree(anyString());
    }

    @Test
    @DisplayName("실패-stringToJsonNode()-JsonProcessingException")
    void stringToJsonNode_ThrowsJsonProcessingException() throws JsonProcessingException {
        //given
        //when
        when(objectMapper.readTree(rawJsonSampleInvalid)).thenThrow(JsonProcessingException.class);
        //then
        assertThrows(JsonProcessingException.class, () -> jsonDeserializerService.stringToJsonNode(rawJsonSampleInvalid));
        verify(objectMapper, times(1)).readTree(anyString());
    }

    @Test
    @DisplayName("성공-jsonNodeToPOJO()")
    void jsonNodeToPOJO_Success() throws IOException {
        //given
        //when
        when(objectMapper.readerFor(OpenApiResponse.class).readValue(jsonNodeSample)).thenReturn(openApiResponseSample);
        //then
        assertEquals(openApiResponseSample, jsonDeserializerService.jsonNodeToPOJO(jsonNodeSample));
        verify(objectMapper.readerFor(OpenApiResponse.class), times(1)).readValue(any(JsonNode.class));
    }

    @Test
    @DisplayName("실패-jsonNodeToPOJO()-IOException")
    void jsonNodeToPOJO_Fail() throws IOException {
        //given
        //when
        when(objectMapper.readerFor(OpenApiResponse.class).readValue(jsonNodeSampleInvalid)).thenThrow(IOException.class);
        //then
        assertThrows(IOException.class, () -> jsonDeserializerService.jsonNodeToPOJO(jsonNodeSampleInvalid));
        verify(objectMapper.readerFor(OpenApiResponse.class), times(1)).readValue(any(JsonNode.class));
    }
}
