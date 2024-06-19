package com.dsadara.realestatebatchservice.processor;

import com.dsadara.realestatebatchservice.domain.RealEstate;
import com.dsadara.realestatebatchservice.dto.RealEstateDto;
import com.dsadara.realestatebatchservice.type.RealEstateType;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDateTime;

public class RealEstateProcessor implements ItemProcessor<RealEstateDto, RealEstate> {

    private final RealEstateType realEstateType;

    public RealEstateProcessor(String realEstateType) {
        this.realEstateType = RealEstateType.valueOf(realEstateType);
    }

    @Override
    public RealEstate process(RealEstateDto realEstateDto) throws Exception {
        LocalDateTime now = LocalDateTime.now();
        return RealEstate.builder()
                .constructYear(Short.valueOf(realEstateDto.getConstructYear()))
                .contractYear(Short.valueOf(realEstateDto.getContractYear()))
                .name(realEstateDto.getName())
                .beopJeongDong(realEstateDto.getBeopJeongDong())
                .contractMonth(Short.valueOf(realEstateDto.getContractMonth()))
                .contractDay(Short.valueOf(realEstateDto.getContractDay()))
                .jeonYongArea(realEstateDto.getJeonYongArea())
                .parcelNumber(realEstateDto.getParcelNumber())
                .beopJeongDongCode(realEstateDto.getBeopJeongDongCode())
                .floor(realEstateDto.getFloor())
                .createdAt(now)
                .realEstateType(realEstateType)
                .build();
    }
}
