package com.dsadara.realestatebatchservice.processor;

import com.dsadara.realestatebatchservice.domain.RealEstate;
import com.dsadara.realestatebatchservice.domain.Rent;
import com.dsadara.realestatebatchservice.domain.Sale;
import com.dsadara.realestatebatchservice.dto.RealEstateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RealEstateItemProcessorTest {

    private RealEstateItemProcessor itemProcessor_sale;
    private RealEstateItemProcessor itemProcessor_rent;

    @BeforeEach
    void setUp() {
        itemProcessor_sale = new RealEstateItemProcessor("APT_TRADE");
        itemProcessor_rent = new RealEstateItemProcessor("APT_RENT");
    }

    @Test
    void process_success_RealEstateAndSale_OneToOneMapped() throws Exception {
        // given
        RealEstateDto realEstateDto = RealEstateDto.builder()
                .constructYear("2020")
                .contractYear("2021")
                .name("강변힐스테이트아파트")
                .beopJeongDong("염창동")
                .contractMonth("1")
                .contractDay("22")
                .jeonYongArea("74")
                .parcelNumber("299")
                .beopJeongDongCode(11500)
                .floor("12")
                .dealAmount("90000")
                .CancelDealType("0")
                .CancelDealDay("23.01.01")
                .dealType("중개거래")
                .agentAddress("서울 강서구")
                .build();

        // when
        RealEstate realEstate = itemProcessor_sale.process(realEstateDto);
        Sale sale = realEstate.getSale();

        // then
        assertEquals(realEstate, sale.getRealEstate());
        assertEquals(Short.valueOf("2020"), realEstate.getConstructYear());
        assertEquals(Short.valueOf("2021"), realEstate.getContractYear());
        assertEquals("강변힐스테이트아파트", realEstate.getName());
        assertEquals("염창동", realEstate.getBeopJeongDong());
        assertEquals(Short.valueOf("1"), realEstate.getContractMonth());
        assertEquals(Short.valueOf("22"), realEstate.getContractDay());
        assertEquals("74", realEstate.getJeonYongArea());
        assertEquals("299", realEstate.getParcelNumber());
        assertEquals(11500, realEstate.getBeopJeongDongCode());
        assertEquals("12", realEstate.getFloor());
        assertEquals(new BigDecimal("90000"), sale.getDealAmount());
        assertEquals("0", sale.getCancelDealType());
        assertEquals("23.01.01", sale.getCancelDealDay());
        assertEquals("중개거래", sale.getDealType());
        assertEquals("서울 강서구", sale.getAgentAddress());
    }

    @Test
    void process_success_RealEstateAndRent_OneToOneMapped() throws Exception {
        // given
        RealEstateDto realEstateDto = RealEstateDto.builder()
                .constructYear("2020")
                .contractYear("2021")
                .name("강변힐스테이트아파트")
                .beopJeongDong("염창동")
                .contractMonth("1")
                .contractDay("22")
                .jeonYongArea("74")
                .parcelNumber("299")
                .beopJeongDongCode(11500)
                .floor("12")
                .requestRenewalRight("사용")
                .contractType("신규")
                .contractPeriod("23.07~25.07")
                .monthlyRent("88")
                .deposit("30000")
                .depositBefore("20000")
                .monthlyRentBefore("77")
                .siGunGu(null)
                .build();

        // when
        RealEstate realEstate = itemProcessor_rent.process(realEstateDto);
        Rent rent = realEstate.getRent();

        // then
        assertEquals(realEstate, rent.getRealEstate());
        assertEquals(Short.valueOf("2020"), realEstate.getConstructYear());
        assertEquals(Short.valueOf("2021"), realEstate.getContractYear());
        assertEquals("강변힐스테이트아파트", realEstate.getName());
        assertEquals("염창동", realEstate.getBeopJeongDong());
        assertEquals(Short.valueOf("1"), realEstate.getContractMonth());
        assertEquals(Short.valueOf("22"), realEstate.getContractDay());
        assertEquals("74", realEstate.getJeonYongArea());
        assertEquals("299", realEstate.getParcelNumber());
        assertEquals(11500, realEstate.getBeopJeongDongCode());
        assertEquals("12", realEstate.getFloor());
        assertEquals("사용", rent.getRequestRenewalRight());
        assertEquals("신규", rent.getContractType());
        assertEquals("23.07~25.07", rent.getContractPeriod());
        assertEquals(new BigDecimal("88"), rent.getMonthlyRent());
        assertEquals(new BigDecimal("30000"), rent.getDeposit());
        assertEquals(new BigDecimal("20000"), rent.getDepositBefore());
        assertEquals(new BigDecimal("77"), rent.getMonthlyRentBefore());
        assertNull(rent.getSiGunGu());
    }
}