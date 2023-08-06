package com.dsadara.realestatebatchservice.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class RealEstateDto {
    // 공통
    @JsonProperty(value = "건축년도")
    private String constructYear;
    @JsonProperty(value = "년")
    private String contractYear;
    @JsonProperty(value = "아파트")
    @JsonAlias({"연립다세대", "단지"})
    private String name;
    @JsonProperty(value = "법정동")
    private String legalDong;
    @JsonProperty(value = "시군구")
    private String siGunGu;
    @JsonProperty(value = "월")
    private String month;
    @JsonProperty(value = "일")
    private String day;
    @JsonProperty(value = "전용면적")
    @JsonAlias("계약면적")
    private String jeonYongArea;
    @JsonProperty(value = "지번")
    private String parcelNumber;
    @JsonProperty(value = "지역코드")
    private String regionCode;
    @JsonProperty(value = "층")
    private String floor;
    // 매매
    @JsonProperty(value = "거래금액")
    private String dealAmount;
    @JsonProperty(value = "해제여부")
    private String CancelDealType;
    @JsonProperty(value = "해제사유발생일")
    private String CancelDealDay;
    @JsonProperty(value = "거래유형")
    private String dealType;
    @JsonProperty(value = "중개사소재지")
    private String agentAddress;
    // 전월세
    @JsonProperty(value = "갱신요구권사용")
    private String requestRenewalRight;
    @JsonProperty(value = "계약구분")
    private String contractType;
    @JsonProperty(value = "계약기간")
    private String contractPeriod;
    @JsonProperty(value = "월세금액")
    @JsonAlias("월세")
    private String monthlyRent;
    @JsonProperty(value = "보증금액")
    @JsonAlias("보증금")
    private String deposit;
    @JsonProperty(value = "종전계약보증금")
    private String depositBefore;
    @JsonProperty(value = "종전계약월세")
    private String monthlyRentBefore;
}
