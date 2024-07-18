package com.dsadara.realestatebatchservice.processor;

import com.dsadara.realestatebatchservice.domain.RealEstate;
import com.dsadara.realestatebatchservice.domain.Rent;
import com.dsadara.realestatebatchservice.domain.Sale;
import com.dsadara.realestatebatchservice.dto.RealEstateDto;
import com.dsadara.realestatebatchservice.type.RealEstateType;
import org.springframework.batch.item.ItemProcessor;

public class RealEstateItemProcessor implements ItemProcessor<RealEstateDto, RealEstate> {

    private final RealEstateType realEstateType;

    public RealEstateItemProcessor(String realEstateType) {
        this.realEstateType = RealEstateType.valueOf(realEstateType);
    }

    @Override
    public RealEstate process(RealEstateDto realEstateDto) throws Exception {
        RealEstate realEstate = new RealEstate(realEstateDto, realEstateType);

        if (realEstateType.equals(RealEstateType.APT_TRADE)) {      // 매매 타입일 경우
            Sale sale = new Sale(realEstateDto, realEstate);
            realEstate.setSale(sale);
        } else {                                                    // 전월세 타입일 경우
            Rent rent = new Rent(realEstateDto, realEstate);
            realEstate.setRent(rent);
        }
        return realEstate;
    }
}
