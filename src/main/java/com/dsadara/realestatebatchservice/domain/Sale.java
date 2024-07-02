package com.dsadara.realestatebatchservice.domain;

import com.dsadara.realestatebatchservice.dto.RealEstateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.springframework.util.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sale {
    @Id
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

    public void setNumeralFields(RealEstateDto realEstateDto) {
        if (StringUtils.hasText(realEstateDto.getDealAmount())) {
            this.dealAmount = new BigDecimal(realEstateDto.getDealAmount().replace(",", ""));
        }
    }
}
