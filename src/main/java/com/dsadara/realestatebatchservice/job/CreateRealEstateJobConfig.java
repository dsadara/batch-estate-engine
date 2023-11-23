package com.dsadara.realestatebatchservice.job;

import com.dsadara.realestatebatchservice.dto.RealEstateDto;
import com.dsadara.realestatebatchservice.entity.RealEstate;
import com.dsadara.realestatebatchservice.repository.RealEstateRepository;
import com.dsadara.realestatebatchservice.service.GenerateApiQueryParam;
import com.dsadara.realestatebatchservice.service.RequestDataAsync;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.HttpServerErrorException;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class CreateRealEstateJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final RealEstateRepository realEstateRepository;
    private final JdbcTemplate jdbcTemplate;
    private final RequestDataAsync requestDataAsync;
    private final GenerateApiQueryParam generateApiQueryParam;

    @Bean
    public Job createRealEstateJob() {
        return jobBuilderFactory.get("createRealEstateJob")
                .incrementer(new RunIdIncrementer())
                .start(createAptRentStep())
                .build();
    }

    @Bean
    @JobScope
    public Step createAptRentStep() {
        return stepBuilderFactory.get("createAptRentStep")
                .<RealEstateDto, RealEstate>chunk(10000)
                .reader(createApiItemReader(null, null))
                .processor(createRealEstateProcessor())
                .writer(createJdbcRealEstateWriter())
                .faultTolerant()
                .skip(HttpServerErrorException.class)
                .skip(ExecutionException.class)
                .skipLimit(10000)
                .build();
    }

    @Bean
    @StepScope
    public ApiItemReader createApiItemReader(
            @Value("${openapi.url.apt-rent}") String baseUrl,
            @Value("${openapi.key}") String serviceKey) {
        return new ApiItemReader(baseUrl, serviceKey, requestDataAsync, generateApiQueryParam);
    }

    @Bean
    public ItemProcessor<RealEstateDto, RealEstate> createRealEstateProcessor() {
        return RealEstateDto -> {
            LocalDateTime now = LocalDateTime.now();
            return RealEstate.builder()
                    .constructYear(RealEstateDto.getConstructYear())
                    .contractYear(RealEstateDto.getContractYear())
                    .name(RealEstateDto.getName())
                    .legalDong(RealEstateDto.getLegalDong())
                    .siGunGu(RealEstateDto.getSiGunGu())
                    .month(RealEstateDto.getMonth())
                    .day(RealEstateDto.getDay())
                    .jeonYongArea(RealEstateDto.getJeonYongArea())
                    .parcelNumber(RealEstateDto.getParcelNumber())
                    .regionCode(RealEstateDto.getRegionCode())
                    .floor(RealEstateDto.getFloor())
                    .dealAmount(RealEstateDto.getDealAmount())
                    .CancelDealType(RealEstateDto.getCancelDealType())
                    .CancelDealDay(RealEstateDto.getCancelDealDay())
                    .dealType(RealEstateDto.getDealType())
                    .agentAddress(RealEstateDto.getAgentAddress())
                    .requestRenewalRight(RealEstateDto.getRequestRenewalRight())
                    .contractType(RealEstateDto.getContractType())
                    .contractPeriod(RealEstateDto.getContractPeriod())
                    .monthlyRent(RealEstateDto.getMonthlyRent())
                    .deposit(RealEstateDto.getDeposit())
                    .depositBefore(RealEstateDto.getDepositBefore())
                    .monthlyRentBefore(RealEstateDto.getMonthlyRentBefore())
                    .createdAt(now)
                    .build();
        };
    }

    @Bean
    public RepositoryItemWriter<RealEstate> createRealEstateWriter() {
        return new RepositoryItemWriterBuilder<RealEstate>()
                .repository(realEstateRepository)
                .build();
    }

    @Bean
    public ItemWriter<RealEstate> createJdbcRealEstateWriter() {
        return items -> jdbcTemplate.batchUpdate("insert into REAL_ESTATE (" +
                        "cancel_deal_day," +
                        "cancel_deal_type," +
                        "agent_address," +
                        "construct_year," +
                        "contract_period," +
                        "contract_type," +
                        "contract_year," +
                        "created_at," +
                        "days," +
                        "deal_amount," +
                        "deal_type," +
                        "deposit," +
                        "deposit_before," +
                        "floor," +
                        "jeon_yong_area," +
                        "legal_dong," +
                        "months," +
                        "monthly_rent," +
                        "monthly_rent_before," +
                        "name," +
                        "parcel_number," +
                        "region_code," +
                        "request_renewal_right," +
                        "si_gun_gu) values (" +
                        "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                items,
                10000,
                (ps, RealEstate) -> {
                    ps.setObject(1, RealEstate.getCancelDealDay());
                    ps.setObject(2, RealEstate.getCancelDealType());
                    ps.setObject(3, RealEstate.getAgentAddress());
                    ps.setObject(4, RealEstate.getConstructYear());
                    ps.setObject(5, RealEstate.getContractPeriod());
                    ps.setObject(6, RealEstate.getContractType());
                    ps.setObject(7, RealEstate.getContractYear());
                    ps.setObject(8, RealEstate.getCreatedAt());
                    ps.setObject(9, RealEstate.getDay());
                    ps.setObject(10, RealEstate.getDealAmount());
                    ps.setObject(11, RealEstate.getDealType());
                    ps.setObject(12, RealEstate.getDeposit());
                    ps.setObject(13, RealEstate.getDepositBefore());
                    ps.setObject(14, RealEstate.getFloor());
                    ps.setObject(15, RealEstate.getJeonYongArea());
                    ps.setObject(16, RealEstate.getLegalDong());
                    ps.setObject(17, RealEstate.getMonth());
                    ps.setObject(18, RealEstate.getMonthlyRent());
                    ps.setObject(19, RealEstate.getMonthlyRentBefore());
                    ps.setObject(20, RealEstate.getName());
                    ps.setObject(21, RealEstate.getParcelNumber());
                    ps.setObject(22, RealEstate.getRegionCode());
                    ps.setObject(23, RealEstate.getRequestRenewalRight());
                    ps.setObject(24, RealEstate.getSiGunGu());
                });
    }

}
