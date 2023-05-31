package com.dsadara.realestatebatchservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class AptRentDto {
    @JsonProperty(value = "갱신요구권사용")
    private String requestRenewalRight; // 갱신요구권사용
    @JsonProperty(value = "건축년도")
    private Integer constructYear;       // 건축년도
    @JsonProperty(value = "계약구분")
    private String contractType;        // 계약구분
    @JsonProperty(value = "계약기간")
    private String contractPeriod;      // 계약기간
    @JsonProperty(value = "년")
    private Integer contractYear;        // 년
    @JsonProperty(value = "아파트")
    private String name;                // 아파트
    @JsonProperty(value = "법정동")
    private String legalDong;           // 법정동
    @JsonProperty(value = "보증금액")
    private String deposit;             // 보증금액
    @JsonProperty(value = "시군구")
    private String siGunGu;             // 시군구
    @JsonProperty(value = "월")
    private Integer month;               // 월
    @JsonProperty(value = "월세금액")
    private Integer monthlyRent;         // 월세금액
    @JsonProperty(value = "일")
    private Integer day;                 // 일
    @JsonProperty(value = "전용면적")
    private Double jeonYongArea;        // 전용면적
    @JsonProperty(value = "종전계약보증금")
    private Integer depositBefore;       // 종전계약보증금
    @JsonProperty(value = "종전계약월세")
    private Integer monthlyRentBefore;   // 종전계약월세
    @JsonProperty(value = "지번")
    private Integer parcelNumber;        // 지번
    @JsonProperty(value = "지역코드")
    private Integer regionCode;          // 지역코드
    @JsonProperty(value = "층")
    private Integer floor;               // 층
}
