package com.dsadara.realestatebatchservice.service;

import com.dsadara.realestatebatchservice.dto.RealEstateDto;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class JsonDeserializerRealTest {

    @Autowired
    private JsonDeserializer jsonDeserializer;

    @Test
    @DisplayName("성공-jsonNodeToPOJO()-dto에 매핑되는 json field가 없는 경우")
    public void jsonNodeToPOJO_Success_NoJsonField() throws Exception {
        // given
        String rawJson = "{\"response\":{\"body\":{\"items\":" +
                "{\"item\":[" +
                "{\"건축년도\":1998}," +
                "{\"매핑되지않는필드\":1994}" +
                "]},\"numOfRows\":10,\"pageNo\":1,\"totalCount\":2}}}";
        Optional<JsonNode> optionalJsonNode = jsonDeserializer.stringToJsonNode(rawJson);

        // when
        Optional<List<RealEstateDto>> realEstateDataDtosOptional =
                jsonDeserializer.jsonNodeToPOJO(optionalJsonNode);

        // then
        assertEquals("1998", realEstateDataDtosOptional.get().get(0).getConstructYear());
        assertEquals(null, realEstateDataDtosOptional.get().get(1).getConstructYear());
    }

    @Test
    @DisplayName("성공-jsonNodeToPOJO()-single value를 List로 매핑 시 타입 불일치")
    public void jsonNodeToPOJO_Success_TypeMisMatch() throws Exception {
        //given
        String rawJson = "{\"response\":{\"body\":{\"items\":" +
                "{\"item\":" +
//                "[" +
                "{\"건축년도\":1998}" +
//                "]" +
                "},\"numOfRows\":10,\"pageNo\":1,\"totalCount\":1}}}";
        Optional<JsonNode> optionalJsonNode = jsonDeserializer.stringToJsonNode(rawJson);

        //when
        Optional<List<RealEstateDto>> realEstateDataDtosOptional =
                jsonDeserializer.jsonNodeToPOJO(optionalJsonNode);

        //then
        assertEquals("1998", realEstateDataDtosOptional.get().get(0).getConstructYear());
    }

}
