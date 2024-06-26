package com.dsadara.realestatebatchservice.config;

import com.dsadara.realestatebatchservice.domain.RealEstate;
import com.dsadara.realestatebatchservice.domain.RealEstateRepository;
import com.dsadara.realestatebatchservice.domain.Rent;
import com.dsadara.realestatebatchservice.domain.RentRepository;
import com.dsadara.realestatebatchservice.domain.Sale;
import com.dsadara.realestatebatchservice.domain.SaleRepository;
import com.dsadara.realestatebatchservice.dto.RealEstateDto;
import com.dsadara.realestatebatchservice.listener.SlaveStepFailureLimitListener;
import com.dsadara.realestatebatchservice.listener.StepExceptionLogger;
import com.dsadara.realestatebatchservice.processor.RealEstateItemProcessor;
import com.dsadara.realestatebatchservice.processor.RentProcessor;
import com.dsadara.realestatebatchservice.processor.SaleProcessor;
import com.dsadara.realestatebatchservice.reader.ApiItemReader;
import com.dsadara.realestatebatchservice.service.ApiRequester;
import com.dsadara.realestatebatchservice.service.GenerateApiQueryParam;
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
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.Collections;
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
                .<RealEstateDto, Object>chunk(100)
                .reader(createApiItemReader(null, null))
                .processor(createCompositeItemProcessor(null))
                .writer(createCompositeItemWriter())
                .listener(stepExceptionLogger)
                .listener(slaveStepFailureLimitListener)
                .build();
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
    public CompositeItemProcessor<RealEstateDto, Object> createCompositeItemProcessor(@Value("#{jobParameters['realEstateType']}") String realRealEstateType) {
        CompositeItemProcessor<RealEstateDto, Object> processor = new CompositeItemProcessor<>();

        List<ItemProcessor<RealEstateDto, ?>> processors = new ArrayList<>();
        processors.add(new RealEstateItemProcessor(realRealEstateType));
        if (realRealEstateType.equals("아파트매매")) {     // 매매 데이터 처리
            processors.add(new SaleProcessor());
        } else {                                        // 전월세 데이터 처리
            processors.add(new RentProcessor());
        }

        processor.setDelegates(processors);
        return processor;
    }

    @Bean
    public RepositoryItemWriter<RealEstate> createRealEstateWriter() {
        return new RepositoryItemWriterBuilder<RealEstate>()
                .repository(realEstateRepository)
                .build();
    }

    @Bean
    public RepositoryItemWriter<Rent> createRentWriter() {
        return new RepositoryItemWriterBuilder<Rent>()
                .repository(rentRepository)
                .build();
    }

    @Bean
    public RepositoryItemWriter<Sale> createSaleWriter() {
        return new RepositoryItemWriterBuilder<Sale>()
                .repository(saleRepository)
                .build();
    }

    @Bean
    public ItemWriter<Object> createCompositeItemWriter() {
        return items -> {
            for (Object item : items) {
                if (item instanceof RealEstate) {
                    createRealEstateWriter().write(Collections.singletonList((RealEstate) item));
                } else if (item instanceof Rent) {
                    createRentWriter().write(Collections.singletonList((Rent) item));
                } else if (item instanceof Sale) {
                    createSaleWriter().write(Collections.singletonList((Sale) item));
                }
            }
        };
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

}