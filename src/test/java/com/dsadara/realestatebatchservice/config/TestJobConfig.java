package com.dsadara.realestatebatchservice.config;

import com.dsadara.realestatebatchservice.domain.RealEstate;
import com.dsadara.realestatebatchservice.dto.RealEstateDto;
import com.dsadara.realestatebatchservice.listener.FailedStepCounter;
import com.dsadara.realestatebatchservice.listener.StepExceptionLogger;
import com.dsadara.realestatebatchservice.service.GenerateApiQueryParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EnableBatchProcessing
@Profile("test")
@Slf4j
@RequiredArgsConstructor
@Configuration
public class TestJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final GenerateApiQueryParam generateApiQueryParam;
    private final StepExceptionLogger stepExceptionLogger;
    private final ItemReader<RealEstateDto> createEmptyItemReader;
    private final FailedStepCounter failedStepCounter;

    @Bean
    public Job TestJob() throws Exception {
        return jobBuilderFactory.get("testJob")
                .incrementer(new RunIdIncrementer())
                .listener(failedStepCounter)
                .flow(testMasterStep(null))
                .end()
                .build();
    }

    @Bean
    @JobScope
    public Step testMasterStep(@Value("#{jobParameters['bjdCode']}") String bjdCode) throws Exception {
        return stepBuilderFactory.get("법정동코드" + ":" + bjdCode)
                .partitioner("slaveStep", testDealYearMonthPartitioner())
                .step(testSlaveStep())
                .gridSize(300)
                .build();
    }

    @Bean
    @JobScope
    public Step testSlaveStep() throws Exception {
        return stepBuilderFactory.get("계약월")
                .<RealEstateDto, RealEstate>chunk(100)
                .reader(createEmptyItemReader)
                .processor(createEmptyProcessor())
                .writer(createEmptyWriter())
                .listener(stepExceptionLogger)
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<RealEstateDto> createEmptyItemReader(
            @Value("#{jobParameters['baseUrl']}") String baseUrl,
            @Value("#{jobParameters['serviceKey']}") String serviceKey) throws Exception {
        return () -> null;
    }

    @Bean
    public ItemProcessor<RealEstateDto, RealEstate> createEmptyProcessor() {
        return RealEstateDto -> new RealEstate();
    }

    @Bean
    public ItemWriter<RealEstate> createEmptyWriter() {
        return items -> {
        };
    }

    @Bean
    public Partitioner testDealYearMonthPartitioner() {
        return gridSize -> {
            Map<String, ExecutionContext> result = new HashMap<>();
            List<String> dealYearMonthList = generateApiQueryParam.getDealYearMonthsList();
            for (String dealYearMonth : dealYearMonthList) {
                ExecutionContext executionContext = new ExecutionContext();
                executionContext.putString("dealYearMonth", dealYearMonth);
                result.put(dealYearMonth, executionContext);
            }
            return result;
        };
    }

}
