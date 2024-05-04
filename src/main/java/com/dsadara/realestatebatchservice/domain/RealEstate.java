package com.dsadara.realestatebatchservice.domain;

import com.dsadara.realestatebatchservice.type.RealEstateType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "real_estate", indexes = @Index(name = "idx_real_estate_type", columnList = "realEstateType", unique = true))
public class RealEstate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    private LocalDateTime createdAt;
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
    @Column(name = "months")
    private String month;
    @Comment("일")
    @Column(name = "days")
    private String day;
    @Comment("전용면적, 계약면적")
    private String jeonYongArea;
    @Comment("지번")
    private String parcelNumber;
    @Comment("지역코드")
    private String regionCode;
    @Comment("층")
    private String floor;
    @Comment("부동산 종류")
    @Enumerated(EnumType.STRING)
    private RealEstateType realEstateType;
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

}
