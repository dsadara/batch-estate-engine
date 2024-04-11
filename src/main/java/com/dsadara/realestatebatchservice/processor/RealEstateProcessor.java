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
                .constructYear(realEstateDto.getConstructYear())
                .contractYear(realEstateDto.getContractYear())
                .name(realEstateDto.getName())
                .legalDong(realEstateDto.getLegalDong())
                .siGunGu(realEstateDto.getSiGunGu())
                .month(realEstateDto.getMonth())
                .day(realEstateDto.getDay())
                .jeonYongArea(realEstateDto.getJeonYongArea())
                .parcelNumber(realEstateDto.getParcelNumber())
                .regionCode(realEstateDto.getRegionCode())
                .floor(realEstateDto.getFloor())
                .dealAmount(realEstateDto.getDealAmount())
                .CancelDealType(realEstateDto.getCancelDealType())
                .CancelDealDay(realEstateDto.getCancelDealDay())
                .dealType(realEstateDto.getDealType())
                .agentAddress(realEstateDto.getAgentAddress())
                .requestRenewalRight(realEstateDto.getRequestRenewalRight())
                .contractType(realEstateDto.getContractType())
                .contractPeriod(realEstateDto.getContractPeriod())
                .monthlyRent(realEstateDto.getMonthlyRent())
                .deposit(realEstateDto.getDeposit())
                .depositBefore(realEstateDto.getDepositBefore())
                .monthlyRentBefore(realEstateDto.getMonthlyRentBefore())
                .createdAt(now)
                .realEstateType(realEstateType)
                .build();
    }
}
