package com.dsadara.realestatebatchservice.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RealEstateDto {
    // 공통 (RealEstate)
    @JsonProperty(value = "건축년도")
    private String constructYear;
    @JsonProperty(value = "년")
    private String contractYear;
    @JsonProperty(value = "아파트")
    @JsonAlias({"연립다세대", "단지"})
    private String name;
    @JsonProperty(value = "법정동")
    private String beopJeongDong;
    @JsonProperty(value = "월")
    private String contractMonth;
    @JsonProperty(value = "일")
    private String contractDay;
    @JsonProperty(value = "전용면적")
    @JsonAlias("계약면적")
    private String jeonYongArea;
    @JsonProperty(value = "지번")
    private String parcelNumber;
    @JsonProperty(value = "지역코드")
    private Integer beopJeongDongCode;
    @JsonProperty(value = "층")
    private String floor;

    // 매매 (Sale)
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
    @JsonProperty(value = "시군구")
    private String siGunGu;

}
