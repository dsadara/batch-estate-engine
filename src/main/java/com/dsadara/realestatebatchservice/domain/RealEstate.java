package com.dsadara.realestatebatchservice.domain;

import com.dsadara.realestatebatchservice.type.RealEstateType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.math.BigDecimal;
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
    private int id;
    @CreationTimestamp
    private LocalDateTime createdAt;
    // 공통
    @Comment("갱신요구권사용")
    private String requestRenewalRight;
    @Comment("건축년도")
    private Short constructYear;
    @Comment("계약년")
    private Short contractYear;
    @Comment("이름")
    private String name;
    @Comment("법정동")
    private String beopJeongDong;
    @Comment("계약월")
    private Short contractMonth;
    @Comment("계약일")
    private Short contractDay;
    @Comment("전용면적, 계약면적")
    private String jeonYongArea;
    @Comment("지번")
    private String parcelNumber;
    @Comment("지역코드")
    private String beopJeongDongCode;
    @Comment("층")
    private String floor;
    @Comment("부동산 종류")
    @Enumerated(EnumType.STRING)
    private RealEstateType realEstateType;
    // 아파트 매매
    @Comment("거래금액")
    private BigDecimal dealAmount;
    @Comment("해제 여부")
    private String CancelDealType;
    @Comment("해제사유 발생일")
    private String CancelDealDay;
    @Comment("거래유형")
    private String dealType;
    @Comment("중개사 소재지")
    private String agentAddress;
    // 전월세
    @Comment("계약구분")
    private String contractType;
    @Comment("계약기간")
    private String contractPeriod;
    @Comment("월세")
    private BigDecimal monthlyRent;
    @Comment("보증금")
    private BigDecimal deposit;
    @Comment("종전 계약 보증금")
    private BigDecimal depositBefore;
    @Comment("종전 계약 월세")
    private BigDecimal monthlyRentBefore;
    @Comment("시군구")
    private String siGunGu;

}
