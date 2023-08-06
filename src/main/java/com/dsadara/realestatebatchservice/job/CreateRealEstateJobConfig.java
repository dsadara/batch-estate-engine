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
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
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
                .start(createRealEstateStep())
                .build();
    }

    @Bean
    public Step createRealEstateStep() throws Exception {
        return stepBuilderFactory.get("createRealEstateStep")
                .<RealEstateDto, RealEstate>chunk(100)
                .reader(createRealEstateListReader())
                .processor(createRealEstateProcessor())
                .writer(createRealEstateWriter())
                .build();
    }

    @Bean
    public ListItemReader<RealEstateDto> createRealEstateListReader() throws Exception {
        String baseUrl = "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptRent";
        String legalDongCode = "11200";
        String contractYMD = "202207";
        String serviceKey = "KNxUoxDnwzkyp3fb8dOjCWatfWm6VdGxJHzwOlvkSAcOcm%2B6%2BgIsOrcZ8Wr8hU0qzcmNE2tSjG7HUQBIA%2FqkYg%3D%3D";
        return new ListItemReader<RealEstateDto>(
                requestData.requestData(baseUrl, legalDongCode, contractYMD, serviceKey)
        );
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
