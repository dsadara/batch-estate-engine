package com.dsadara.realestatebatchservice.job;

import com.dsadara.realestatebatchservice.dto.RealEstateDto;
import com.dsadara.realestatebatchservice.entity.RealEstate;
import com.dsadara.realestatebatchservice.repository.RealEstateRepository;
import com.dsadara.realestatebatchservice.service.RequestData;
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
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    private final RequestData requestData;

    @Bean
    public Job createRealEstateJob() throws Exception {
        return jobBuilderFactory.get("createRealEstateJob")
                .incrementer(new RunIdIncrementer())
                .start(createAptRentStep())
                .build();
    }

    @Bean
    @JobScope
    public Step createAptRentStep() throws Exception {
        return stepBuilderFactory.get("createAptRentStep")
                .<RealEstateDto, RealEstate>chunk(100)
                .reader(createApiItemReader(null, null))
                .processor(createRealEstateProcessor())
                .writer(createRealEstateWriter())
                .faultTolerant()
                .skip(HttpServerErrorException.class)
                .skip(ExecutionException.class)
                .skipLimit(10000)
                .listener(new StepExceptionLogger())
                .build();
    }

    @Bean
    @StepScope
    public ApiItemReader createApiItemReader(
            @Value("${openapi.request.url.aptRent}") String baseUrl,
            @Value("${openapi.request.serviceKey}") String serviceKey) throws Exception {
        return new ApiItemReader(baseUrl, serviceKey, "11110", "201501", requestData);
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

}
