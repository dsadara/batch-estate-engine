package com.dsadara.realestatebatchservice.entity;

import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RealEstate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 공통
    @Comment("건축년도")
    private String constructYear;
    @Comment("년")
    private String contractYear;
    @Comment("아파트, 연립다세대, 단지")
    private String name;
    @Comment("법정동")
    private String legalDong;
    @Comment("시군구")
    private String siGunGu;
    @Comment("월")
    private String month;
    @Comment("일")
    private String day;
    @Comment("전용면적, 계약면적")
    private String jeonYongArea;
    @Comment("지번")
    private String parcelNumber;
    @Comment("지역코드")
    private String regionCode;
    @Comment("층")
    private String floor;
    // 매매
    @Comment("거래금액")
    private String dealAmount;
    @Comment("해제여부")
    private String CancelDealType;
    @Comment("해제사유발생일")
    private String CancelDealDay;
    @Comment("거래유형")
    private String dealType;
    @Comment("중개사소재지")
    private String agentAddress;
    // 전월세
    @Comment("갱신요구권사용")
    private String requestRenewalRight;
    @Comment("계약구분")
    private String contractType;
    @Comment("계약기간")
    private String contractPeriod;
    @Comment("월세금액, 월세")
    private String monthlyRent;
    @Comment("보증금액, 보증금")
    private String deposit;
    @Comment("종전계약보증금")
    private String depositBefore;
    @Comment("종전계약월세")
    private String monthlyRentBefore;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
