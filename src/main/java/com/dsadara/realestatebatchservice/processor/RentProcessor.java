package com.dsadara.realestatebatchservice.processor;

import com.dsadara.realestatebatchservice.domain.Rent;
import com.dsadara.realestatebatchservice.dto.RealEstateDto;
import org.springframework.batch.item.ItemProcessor;

import java.math.BigDecimal;

public class RentProcessor implements ItemProcessor<RealEstateDto, Rent> {
    @Override
    public Rent process(RealEstateDto realEstateDto) throws Exception {
        return Rent.builder()
                .requestRenewalRight(realEstateDto.getRequestRenewalRight())
                .contractType(realEstateDto.getContractType())
                .contractPeriod(realEstateDto.getContractPeriod())
                .deposit(new BigDecimal(realEstateDto.getDeposit()))
                .depositBefore(new BigDecimal(realEstateDto.getDepositBefore()))
                .monthlyRent(new BigDecimal(realEstateDto.getMonthlyRent()))
                .monthlyRentBefore(new BigDecimal(realEstateDto.getMonthlyRentBefore()))
                .siGunGu(realEstateDto.getSiGunGu())
                .build();
    }
}
