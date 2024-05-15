package com.dsadara.realestatebatchservice.processor;

import com.dsadara.realestatebatchservice.domain.RealEstate;
import com.dsadara.realestatebatchservice.dto.RealEstateDto;
import com.dsadara.realestatebatchservice.type.RealEstateType;
import org.springframework.batch.item.ItemProcessor;

import java.math.BigDecimal;
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
                .beopJeongDong(realEstateDto.getLegalDong())
                .siGunGu(realEstateDto.getSiGunGu())
                .contractMonth(Short.valueOf(realEstateDto.getMonth()))
                .contractDay(Short.valueOf(realEstateDto.getDay()))
                .jeonYongArea(realEstateDto.getJeonYongArea())
                .parcelNumber(realEstateDto.getParcelNumber())
                .beopJeongDongCode(realEstateDto.getRegionCode())
                .floor(realEstateDto.getFloor())
                .dealAmount(new BigDecimal(realEstateDto.getDealAmount()))
                .CancelDealType(realEstateDto.getCancelDealType())
                .CancelDealDay(realEstateDto.getCancelDealDay())
                .dealType(realEstateDto.getDealType())
                .agentAddress(realEstateDto.getAgentAddress())
                .requestRenewalRight(realEstateDto.getRequestRenewalRight())
                .contractType(realEstateDto.getContractType())
                .contractPeriod(realEstateDto.getContractPeriod())
                .monthlyRent(new BigDecimal(realEstateDto.getMonthlyRent()))
                .deposit(new BigDecimal(realEstateDto.getDeposit()))
                .depositBefore(new BigDecimal(realEstateDto.getDepositBefore()))
                .monthlyRentBefore(new BigDecimal(realEstateDto.getMonthlyRentBefore()))
                .createdAt(now)
                .realEstateType(realEstateType)
                .build();
    }
}
