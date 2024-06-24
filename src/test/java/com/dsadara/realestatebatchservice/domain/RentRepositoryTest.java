package com.dsadara.realestatebatchservice.domain;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class RentRepositoryTest {

    @Autowired
    private RentRepository rentRepository;
    @Autowired
    private RealEstateRepository realEstateRepository;

    @Test
    public void saveRent_success_checkIdisSame() throws Exception {
        // given
        RealEstate realEstate = new RealEstate();
        realEstateRepository.save(realEstate);
        Rent rent = new Rent();
        rent.setRealEstate(realEstate);

        // when
        rentRepository.save(rent);

        // then
        Assertions.assertTrue(rentRepository.existsById(realEstate.getId()));
    }

    @Test
    public void saveRent_fail_doNotSetRealEstate() throws Exception {
        // given
        RealEstate realEstate = new RealEstate();
        realEstateRepository.save(realEstate);
        Rent rent = new Rent();

        // when
        // then
        Assertions.assertThrows(RuntimeException.class, () -> rentRepository.save(rent));
    }
}
