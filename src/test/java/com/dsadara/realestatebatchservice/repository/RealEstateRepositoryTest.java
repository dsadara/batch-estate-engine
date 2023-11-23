package com.dsadara.realestatebatchservice.repository;

import com.dsadara.realestatebatchservice.entity.RealEstate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RealEstateRepositoryTest {

    @Autowired
    private RealEstateRepository realEstateRepository;

    @Test
    public void saveRealEstate_Success() throws Exception {
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
    public void findRealEstate_Success() throws Exception {
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

}
