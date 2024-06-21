package com.dsadara.realestatebatchservice.processor;

import com.dsadara.realestatebatchservice.domain.Sale;
import com.dsadara.realestatebatchservice.dto.RealEstateDto;
import org.springframework.batch.item.ItemProcessor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SaleProcessor implements ItemProcessor<RealEstateDto, Sale> {
    @Override
    public Sale process(RealEstateDto realEstateDto) throws Exception {
        LocalDateTime now = LocalDateTime.now();
        return Sale.builder()
                .CancelDealDay(realEstateDto.getCancelDealDay())
                .CancelDealType(realEstateDto.getCancelDealType())
                .agentAddress(realEstateDto.getAgentAddress())
                .dealAmount(new BigDecimal(realEstateDto.getDealAmount()))
                .dealType(realEstateDto.getDealType())
                .build();
    }
}
