package com.dsadara.realestatebatchservice.domain;

import com.dsadara.realestatebatchservice.type.RealEstateType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
public class RealEstateRepositoryTest {

    @Autowired
    private RealEstateRepository realEstateRepository;

    @Test
    public void saveRealEstate_Success() {
        //given
        RealEstate realEstate = new RealEstate();
        realEstate.setAgentAddress("염창동");
        realEstate.setName("강변힐스테이트");

        //when
        RealEstate saved = realEstateRepository.save(realEstate);

        //then
        assertEquals("염창동", saved.getAgentAddress());
        assertEquals("강변힐스테이트", saved.getName());
    }

    @Test
    public void findRealEstate_Success() {
        //given
        RealEstate realEstate = new RealEstate();
        realEstate.setAgentAddress("염창동");
        realEstate.setName("강변힐스테이트");
        RealEstate saved = realEstateRepository.save(realEstate);

        //when
        List<RealEstate> realEstates = realEstateRepository.findAll();

        //then
        assertEquals("염창동", realEstates.get(0).getAgentAddress());
        assertEquals("강변힐스테이트", realEstates.get(0).getName());
    }

    @Test
    public void findRealEstateType() throws Exception {
        // given
        RealEstate realEstate = RealEstate.builder()
                .realEstateType(RealEstateType.APT_RENT)
                .build();
        RealEstate savedRealEstate = realEstateRepository.save(realEstate);

        // when
        RealEstate findRealEstate = realEstateRepository.findById(savedRealEstate.getId()).get();

        // then
        Assertions.assertEquals(RealEstateType.APT_RENT, findRealEstate.getRealEstateType());
    }

}
