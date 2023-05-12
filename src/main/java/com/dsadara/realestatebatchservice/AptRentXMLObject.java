package com.dsadara.realestatebatchservice;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Builder
@Getter
public class AptRentXMLObject {
    private String requestRenewalRight; // 갱신요구권사용
    private String constructYear;       // 건축년도
    private String contractType;        // 계약구분
    private String contractPeriod;      // 계약기간
    private String contractYear;        // 년
    private String name;                // 아파트
    private String legalDong;           // 법정동
    private String deposit;             // 보증금액
    private String siGunGu;             // 시군구
    private String month;               // 월
    private String monthlyRent;         // 월세금액
    private String day;                 // 일
    private String jeonYongArea;        // 전용면적
    private String depositBefore;       // 종전계약보증금
    private String monthlyRentBefore;   // 종전계약월세
    private String parcelNumber;        // 지번
    private String regionCode;          // 지역코드
    private String floor;               // 층
}
