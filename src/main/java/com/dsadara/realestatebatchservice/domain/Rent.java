package com.dsadara.realestatebatchservice.domain;

import com.dsadara.realestatebatchservice.dto.RealEstateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

import static com.dsadara.realestatebatchservice.test.utils.StringValidator.checkNumeric;
import static com.dsadara.realestatebatchservice.test.utils.StringValidator.trimNumeric;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Rent {
    @Id
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

    public void setNumeralFields(RealEstateDto realEstateDto) {
        if (checkNumeric(realEstateDto.getDeposit())) {
            this.deposit = new BigDecimal(trimNumeric(realEstateDto.getDeposit()));
        }
        if (checkNumeric(realEstateDto.getDepositBefore())) {
            this.depositBefore = new BigDecimal(trimNumeric(realEstateDto.getDepositBefore()));
        }
        if (checkNumeric(realEstateDto.getMonthlyRent())) {
            this.monthlyRent = new BigDecimal(trimNumeric(realEstateDto.getMonthlyRent()));
        }
        if (checkNumeric(realEstateDto.getMonthlyRentBefore())) {
            this.monthlyRentBefore = new BigDecimal(trimNumeric(realEstateDto.getMonthlyRentBefore()));
        }
    }
}
