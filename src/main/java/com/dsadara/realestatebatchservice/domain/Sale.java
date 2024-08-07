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
public class Sale {
    @Id
    private Integer id;

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

    public Sale(RealEstateDto realEstateDto, RealEstate realEstate) {
        setNumeralFields(realEstateDto);
        this.CancelDealDay = realEstateDto.getCancelDealDay();
        this.CancelDealType = realEstateDto.getCancelDealType();
        this.agentAddress = realEstateDto.getAgentAddress();
        this.dealType = realEstateDto.getDealType();
        this.realEstate = realEstate;
    }

    private void setNumeralFields(RealEstateDto realEstateDto) {
        if (checkNumeric(realEstateDto.getDealAmount())) {
            this.dealAmount = new BigDecimal(trimNumeric(realEstateDto.getDealAmount()));
        }
    }
}
