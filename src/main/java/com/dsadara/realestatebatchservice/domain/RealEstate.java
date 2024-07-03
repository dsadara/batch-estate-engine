package com.dsadara.realestatebatchservice.domain;

import com.dsadara.realestatebatchservice.dto.RealEstateDto;
import com.dsadara.realestatebatchservice.type.RealEstateType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.time.LocalDateTime;

import static com.dsadara.realestatebatchservice.test.utils.StringValidator.checkNumeric;
import static com.dsadara.realestatebatchservice.test.utils.StringValidator.trimNumeric;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "real_estate", indexes = @Index(name = "idx_real_estate_type", columnList = "realEstateType"))
public class RealEstate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Comment("건축년도")
    private Short constructYear;
    @Comment("계약년")
    private Short contractYear;
    @Comment("계약일")
    private Short contractDay;
    @Comment("층")
    private String floor;
    @Comment("전용면적, 계약면적")
    private String jeonYongArea;
    @Comment("법정동")
    private String beopJeongDong;
    @Comment("계약월")
    private Short contractMonth;
    @Comment("이름")
    private String name;
    @Comment("지번")
    private String parcelNumber;
    @Comment("부동산 종류")
    @Enumerated(EnumType.STRING)
    private RealEstateType realEstateType;
    @Comment("지역코드")
    private String beopJeongDongCode;
    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "realEstate", cascade = CascadeType.REMOVE)
    @PrimaryKeyJoinColumn
    private Rent rent;
    @OneToOne(mappedBy = "realEstate", cascade = CascadeType.REMOVE)
    @PrimaryKeyJoinColumn
    private Sale sale;

    public void setNumeralFields(RealEstateDto realEstateDto) {
        if (checkNumeric(realEstateDto.getConstructYear())) {
            this.constructYear = Short.valueOf(trimNumeric(realEstateDto.getConstructYear()));
        }
        if (checkNumeric(realEstateDto.getContractYear())) {
            this.contractYear = Short.valueOf(trimNumeric(realEstateDto.getContractYear()));
        }
        if (checkNumeric(realEstateDto.getContractMonth())) {
            this.contractYear = Short.valueOf(trimNumeric(realEstateDto.getContractMonth()));
        }
        if (checkNumeric(realEstateDto.getContractDay())) {
            this.contractYear = Short.valueOf(trimNumeric(realEstateDto.getContractDay()));
        }
    }
}
