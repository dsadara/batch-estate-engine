package com.dsadara.realestatebatchservice.domain;

import org.hibernate.annotations.Comment;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Entity
public class Sale {
    @Id
    @OneToOne(targetEntity = RealEstate.class)
    private int id;

    @Comment("해제사유 발생일")
    private String CancelDealDay;
    @Comment("해제 여부")
    private String CancelDealType;
    @Comment("중개사 소재지")
    private String agentAddress;
    @Comment("거래금액")
    private BigDecimal dealAmount;
    @Comment("거래유형")
    private String dealType;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private RealEstate realEstate;
}