package com.dsadara.realestatebatchservice.domain;

import org.hibernate.annotations.Comment;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Entity
public class Rent {
    @Id
    @OneToOne(targetEntity = RealEstate.class)
    private int id;

    @Comment("갱신요구권사용")
    private String requestRenewalRight;
    @Comment("계약구분")
    private String contractType;
    @Comment("계약기간")
    private String contractPeriod;
    @Comment("보증금")
    private BigDecimal deposit;
    @Comment("종전 계약 보증금")
    private BigDecimal depositBefore;
    @Comment("월세")
    private BigDecimal monthlyRent;
    @Comment("종전 계약 월세")
    private BigDecimal monthlyRentBefore;
    @Comment("시군구")
    private String siGunGu;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private RealEstate realEstate;
}
