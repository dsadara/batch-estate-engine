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
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
                .next(createAptTradeStep())
                .next(createDetachedHouseRentStep())
                .next(createRowHouseRentStep())
                .next(createEfficencyAptRentStep())
                .build();
    }

    @Bean
    @JobScope
    public Step createAptRentStep() throws Exception {
        return stepBuilderFactory.get("createAptRentStep")
                .<RealEstateDto, RealEstate>chunk(1000)
                .reader(createAptRentListReader())
                .processor(createRealEstateProcessor())
                .writer(createRealEstateWriter())
                .build();
    }

    @Bean
    @JobScope
    public Step createAptTradeStep() throws Exception {
        return stepBuilderFactory.get("createAptTradeStep")
                .<RealEstateDto, RealEstate>chunk(1000)
                .reader(createAptTradeListReader())
                .processor(createRealEstateProcessor())
                .writer(createRealEstateWriter())
                .build();
    }

    @Bean
    @JobScope
    public Step createDetachedHouseRentStep() throws Exception {
        return stepBuilderFactory.get("createDetachedHouseRentStep")
                .<RealEstateDto, RealEstate>chunk(1000)
                .reader(createDetachedHouseRentListReader())
                .processor(createRealEstateProcessor())
                .writer(createRealEstateWriter())
                .build();
    }

    @Bean
    @JobScope
    public Step createRowHouseRentStep() throws Exception {
        return stepBuilderFactory.get("createRowHouseRentStep")
                .<RealEstateDto, RealEstate>chunk(1000)
                .reader(createRowHouseRentListReader())
                .processor(createRealEstateProcessor())
                .writer(createRealEstateWriter())
                .build();
    }

    @Bean
    @JobScope
    public Step createEfficencyAptRentStep() throws Exception {
        return stepBuilderFactory.get("createEfficencyAptRentStep")
                .<RealEstateDto, RealEstate>chunk(1000)
                .reader(createEfficencyAptRentListReader())
                .processor(createRealEstateProcessor())
                .writer(createRealEstateWriter())
                .build();
    }

    @Bean
    @StepScope
    public ListItemReader<RealEstateDto> createAptRentListReader() throws Exception {
        return new ListItemReader<>(requestData.requestAptRentData());
    }

    @Bean
    @StepScope
    public ListItemReader<RealEstateDto> createAptTradeListReader() throws Exception {
        return new ListItemReader<>(requestData.requestAptTradeData());
    }

    @Bean
    @StepScope
    public ListItemReader<RealEstateDto> createDetachedHouseRentListReader() throws Exception {
        return new ListItemReader<>(requestData.requestDetachedHouseRent());
    }

    @Bean
    @StepScope
    public ListItemReader<RealEstateDto> createRowHouseRentListReader() throws Exception {
        return new ListItemReader<>(requestData.requestRowHouseRent());
    }

    @Bean
    @StepScope
    public ListItemReader<RealEstateDto> createEfficencyAptRentListReader() throws Exception {
        return new ListItemReader<>(requestData.requestEfficencyAptRent());
    }


    @Bean
    public ItemProcessor<RealEstateDto, RealEstate> createRealEstateProcessor() {
        return RealEstateDto -> RealEstate.builder()
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
                .build();
    }

    @Bean
    public RepositoryItemWriter<RealEstate> createRealEstateWriter() {
        return new RepositoryItemWriterBuilder<RealEstate>()
                .repository(realEstateRepository)
                .build();
    }
}
