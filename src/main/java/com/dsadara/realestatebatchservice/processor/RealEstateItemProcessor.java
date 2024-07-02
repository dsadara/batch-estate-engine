package com.dsadara.realestatebatchservice.processor;

import com.dsadara.realestatebatchservice.domain.RealEstate;
import com.dsadara.realestatebatchservice.domain.Rent;
import com.dsadara.realestatebatchservice.domain.Sale;
import com.dsadara.realestatebatchservice.dto.RealEstateDto;
import com.dsadara.realestatebatchservice.type.RealEstateType;
import org.springframework.batch.item.ItemProcessor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RealEstateItemProcessor implements ItemProcessor<RealEstateDto, RealEstate> {

    private final RealEstateType realEstateType;

    public RealEstateItemProcessor(String realEstateType) {
        this.realEstateType = RealEstateType.valueOf(realEstateType);
    }

    @Override
    public RealEstate process(RealEstateDto realEstateDto) throws Exception {
        RealEstate realEstate = RealEstate.builder()
                .name(realEstateDto.getName())
                .beopJeongDong(realEstateDto.getBeopJeongDong())
                .jeonYongArea(realEstateDto.getJeonYongArea())
                .parcelNumber(realEstateDto.getParcelNumber())
                .beopJeongDongCode(realEstateDto.getBeopJeongDongCode())
                .floor(realEstateDto.getFloor())
                .createdAt(LocalDateTime.now())
                .realEstateType(realEstateType)
                .build();
        realEstate.setNumeralFields(realEstateDto);

        if (realEstateType.equals(RealEstateType.APT_TRADE)) {      // 매매 타입일 경우
            Sale sale = Sale.builder()
                    .CancelDealDay(realEstateDto.getCancelDealDay())
                    .CancelDealType(realEstateDto.getCancelDealType())
                    .agentAddress(realEstateDto.getAgentAddress())
                    .dealAmount(new BigDecimal(realEstateDto.getDealAmount()))
                    .dealType(realEstateDto.getDealType())
                    .realEstate(realEstate)
                    .build();
            sale.setNumeralFields(realEstateDto);
            realEstate.setSale(sale);
        } else {                                                    // 전월세 타입일 경우
            Rent rent = Rent.builder()
                    .requestRenewalRight(realEstateDto.getRequestRenewalRight())
                    .contractType(realEstateDto.getContractType())
                    .contractPeriod(realEstateDto.getContractPeriod())
                    .deposit(new BigDecimal(realEstateDto.getDeposit()))
                    .depositBefore(new BigDecimal(realEstateDto.getDepositBefore()))
                    .monthlyRent(new BigDecimal(realEstateDto.getMonthlyRent()))
                    .monthlyRentBefore(new BigDecimal(realEstateDto.getMonthlyRentBefore()))
                    .siGunGu(realEstateDto.getSiGunGu())
                    .realEstate(realEstate)
                    .build();
            rent.setNumeralFields(realEstateDto);
            realEstate.setRent(rent);
        }
        return realEstate;
    }
}
