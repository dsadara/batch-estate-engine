package com.dsadara.realestatebatchservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
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
        mapItemToAptRentDtos((List<Map<String, Object>>) items.get("item"));
    }

    private void mapItemToAptRentDtos(List<Map<String, Object>> item) {
        aptRentDtos = new LinkedList<>();
        for (Map<String, Object> stringObjectMap : item) {
            AptRentDto aptRentDto = new AptRentDto();
            aptRentDto.setRequestRenewalRight(String.valueOf(stringObjectMap.get("갱신요구권사용")));
            aptRentDto.setConstructYear(String.valueOf(stringObjectMap.get("건축년도")));
            aptRentDto.setContractType(String.valueOf(stringObjectMap.get("계약구분")));
            aptRentDto.setContractPeriod(String.valueOf(stringObjectMap.get("계약기간")));
            aptRentDto.setContractYear(String.valueOf(stringObjectMap.get("년")));
            aptRentDto.setName(String.valueOf(stringObjectMap.get("아파트")));
            aptRentDto.setLegalDong(String.valueOf(stringObjectMap.get("법정동")));
            aptRentDto.setDeposit(String.valueOf(stringObjectMap.get("보증금액")));
            aptRentDto.setSiGunGu(String.valueOf(stringObjectMap.get("시군구")));
            aptRentDto.setMonth(String.valueOf(stringObjectMap.get("월")));
            aptRentDto.setMonthlyRent(String.valueOf(stringObjectMap.get("월세금액")));
            aptRentDto.setDay(String.valueOf(stringObjectMap.get("일")));
            aptRentDto.setJeonYongArea(String.valueOf(stringObjectMap.get("전용면적")));
            aptRentDto.setDepositBefore(String.valueOf(stringObjectMap.get("종전계약보증금")));
            aptRentDto.setMonthlyRentBefore(String.valueOf(stringObjectMap.get("종전계약월세")));
            aptRentDto.setParcelNumber(String.valueOf(stringObjectMap.get("지번")));
            aptRentDto.setRegionCode(String.valueOf(stringObjectMap.get("지역코드")));
            aptRentDto.setFloor(String.valueOf(stringObjectMap.get("층")));
            aptRentDtos.add(aptRentDto);
        }
    }
}