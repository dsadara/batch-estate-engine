package com.dsadara.realestatebatchservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


public class RealEstateDataDto {
    // 공통
    @JsonProperty(value = "건축년도")
    private String constructYear;       // 건축년도
    @JsonProperty(value = "년")
    private String contractYear;        // 년
    @JsonProperty(value = "아파트")
    private String name;                // 이름
    @JsonProperty(value = "법정동")
    private String legalDong;           // 법정동
    @JsonProperty(value = "시군구")
    private String siGunGu;             // 시군구
    @JsonProperty(value = "월")
    private String month;               // 월
    @JsonProperty(value = "일")
    private String day;                 // 일
    @JsonProperty(value = "전용면적")
    private String jeonYongArea;        // 전용면적
    @JsonProperty(value = "지번")
    private String parcelNumber;        // 지번
    @JsonProperty(value = "지역코드")
    private String regionCode;          // 지역코드
    @JsonProperty(value = "층")
    private String floor;               // 층
    // 매매
    @JsonProperty(value = "거래금액")
    private String dealAmount;
    @JsonProperty(value = "해제여부")
    private String CancelDealType;
    @JsonProperty(value = "해제사유발생일")
    private String CancelDealDay;
    @JsonProperty(value = "거래유형")
    private String dealType;
    @JsonProperty(value = "중개업소주소")
    private String agentAddress;
    // 전월세
    @JsonProperty(value = "갱신요구권사용")
    private String requestRenewalRight; // 갱신요구권사용
    @JsonProperty(value = "계약구분")
    private String contractType;        // 계약구분
    @JsonProperty(value = "계약기간")
    private String contractPeriod;      // 계약기간
    @JsonProperty(value = "월세금액")
    private String monthlyRent;         // 월세금액
    @JsonProperty(value = "보증금액")
    private String deposit;             // 보증금액
    @JsonProperty(value = "종전계약보증금")
    private String depositBefore;       // 종전계약보증금
    @JsonProperty(value = "종전계약월세")
    private String monthlyRentBefore;   // 종전계약월세
}
