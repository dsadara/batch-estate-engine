package com.dsadara.realestatebatchservice.writer;

import com.dsadara.realestatebatchservice.domain.RealEstate;
import com.dsadara.realestatebatchservice.domain.RealEstateRepository;
import com.dsadara.realestatebatchservice.domain.RentRepository;
import com.dsadara.realestatebatchservice.domain.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RealEstateItemWriter implements ItemWriter<RealEstate> {

    private final RealEstateRepository realEstateRepository;
    private final RentRepository rentRepository;
    private final SaleRepository saleRepository;

    @Override
    public void write(List<? extends RealEstate> items) throws Exception {
        for (RealEstate realEstate : items) {
            realEstateRepository.save(realEstate);
            if (realEstate.getSale() != null) {
                saleRepository.save(realEstate.getSale());
            } else {
                rentRepository.save(realEstate.getRent());
            }
        }
    }
}
