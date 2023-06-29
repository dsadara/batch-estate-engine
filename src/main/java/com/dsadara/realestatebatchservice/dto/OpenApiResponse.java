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
    private List<RealEstateDataDto> realEstateDataDtos;

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
        realEstateDataDtos = new LinkedList<>();
        for (Map<String, Object> stringObjectMap : item) {
            RealEstateDataDto realEstateDataDto = new RealEstateDataDto();
            realEstateDataDto.setRequestRenewalRight(String.valueOf(stringObjectMap.get("갱신요구권사용")));
            realEstateDataDto.setConstructYear(String.valueOf(stringObjectMap.get("건축년도")));
            realEstateDataDto.setContractType(String.valueOf(stringObjectMap.get("계약구분")));
            realEstateDataDto.setContractPeriod(String.valueOf(stringObjectMap.get("계약기간")));
            realEstateDataDto.setContractYear(String.valueOf(stringObjectMap.get("년")));
            realEstateDataDto.setName(String.valueOf(stringObjectMap.get("아파트")));
            realEstateDataDto.setLegalDong(String.valueOf(stringObjectMap.get("법정동")));
            realEstateDataDto.setDeposit(String.valueOf(stringObjectMap.get("보증금액")));
            realEstateDataDto.setSiGunGu(String.valueOf(stringObjectMap.get("시군구")));
            realEstateDataDto.setMonth(String.valueOf(stringObjectMap.get("월")));
            realEstateDataDto.setMonthlyRent(String.valueOf(stringObjectMap.get("월세금액")));
            realEstateDataDto.setDay(String.valueOf(stringObjectMap.get("일")));
            realEstateDataDto.setJeonYongArea(String.valueOf(stringObjectMap.get("전용면적")));
            realEstateDataDto.setDepositBefore(String.valueOf(stringObjectMap.get("종전계약보증금")));
            realEstateDataDto.setMonthlyRentBefore(String.valueOf(stringObjectMap.get("종전계약월세")));
            realEstateDataDto.setParcelNumber(String.valueOf(stringObjectMap.get("지번")));
            realEstateDataDto.setRegionCode(String.valueOf(stringObjectMap.get("지역코드")));
            realEstateDataDto.setFloor(String.valueOf(stringObjectMap.get("층")));
            realEstateDataDto.setDealAmount(String.valueOf(stringObjectMap.get("거래금액")));
            realEstateDataDto.setCancelDealType(String.valueOf(stringObjectMap.get("해제여부")));
            realEstateDataDto.setCancelDealDay(String.valueOf(stringObjectMap.get("해제사유발생일")));
            realEstateDataDto.setDealType(String.valueOf(stringObjectMap.get("거래유형")));
            realEstateDataDto.setAgentAddress(String.valueOf(stringObjectMap.get("중개업소주소")));
            realEstateDataDtos.add(realEstateDataDto);
        }
    }
}