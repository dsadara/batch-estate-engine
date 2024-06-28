package com.dsadara.realestatebatchservice.config;

import com.dsadara.realestatebatchservice.domain.RealEstate;
import com.dsadara.realestatebatchservice.domain.RealEstateRepository;
import com.dsadara.realestatebatchservice.domain.RentRepository;
import com.dsadara.realestatebatchservice.domain.SaleRepository;
import com.dsadara.realestatebatchservice.dto.RealEstateDto;
import com.dsadara.realestatebatchservice.listener.SlaveStepFailureLimitListener;
import com.dsadara.realestatebatchservice.listener.StepExceptionLogger;
import com.dsadara.realestatebatchservice.processor.RealEstateItemProcessor;
import com.dsadara.realestatebatchservice.reader.ApiItemReader;
import com.dsadara.realestatebatchservice.service.ApiRequester;
import com.dsadara.realestatebatchservice.service.GenerateApiQueryParam;
import com.dsadara.realestatebatchservice.writer.RealEstateItemWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Profile({"local-mysql", "rds-mariadb"})
@Configuration
@Slf4j
@RequiredArgsConstructor
public class RealEstateJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final RealEstateRepository realEstateRepository;
    private final RentRepository rentRepository;
    private final SaleRepository saleRepository;
    private final ApiRequester apiRequester;
    private final GenerateApiQueryParam generateApiQueryParam;
    private final StepExceptionLogger stepExceptionLogger;
    private final SlaveStepFailureLimitListener slaveStepFailureLimitListener;

    @Bean
    public Job createRealEstateJob() throws Exception {
        return jobBuilderFactory.get("realEstateJob")
                .incrementer(new RunIdIncrementer())
                .flow(masterStep(null))
                .end()
                .build();
    }

    @Bean
    @JobScope
    public Step masterStep(@Value("#{jobParameters['bjdCode']}") String bjdCode) throws Exception {
        return stepBuilderFactory.get("법정동코드" + ":" + bjdCode)
                .partitioner("slaveStep", dealYearMonthPartitioner())
                .step(slaveStep())
                .gridSize(300)
                .build();
    }

    @Bean
    @JobScope
    public Step slaveStep() throws Exception {
        return stepBuilderFactory.get("계약월")
                .<RealEstateDto, RealEstate>chunk(100)
                .reader(createApiItemReader(null, null))
                .processor(createRealEstateItemProcessor(null))
                .writer(createRealEstateItemWriter())
                .listener(stepExceptionLogger)
                .listener(slaveStepFailureLimitListener)
                .build();
    }

    @Bean
    public Partitioner dealYearMonthPartitioner() {
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

    @Bean
    @StepScope
    public ApiItemReader createApiItemReader(
            @Value("#{jobParameters['baseUrl']}") String baseUrl,
            @Value("#{jobParameters['serviceKey']}") String serviceKey) throws Exception {
        return new ApiItemReader(baseUrl, serviceKey, apiRequester);
    }

    @Bean
    @StepScope
    public ItemProcessor<RealEstateDto, RealEstate> createRealEstateItemProcessor(
            @Value("#{jobParameters['realEstateType']}") String realEstateType) {
        return new RealEstateItemProcessor(realEstateType);
    }

    @Bean
    public ItemWriter<RealEstate> createRealEstateItemWriter() {
        return new RealEstateItemWriter(realEstateRepository, rentRepository, saleRepository);
    }

}