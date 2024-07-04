package com.dsadara.realestatebatchservice.job;

import com.dsadara.realestatebatchservice.domain.RealEstate;
import com.dsadara.realestatebatchservice.domain.RealEstateRepository;
import com.dsadara.realestatebatchservice.domain.RentRepository;
import com.dsadara.realestatebatchservice.domain.SaleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

@Disabled
@SpringBatchTest
@SpringBootTest
@ActiveProfiles("local-h2")
public class RealEstateJobRealTest {

    @Autowired
    private Environment env;
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    @Autowired
    private RealEstateRepository realEstateRepository;
    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private RentRepository rentRepository;

    @Test
    public void jobLauncher_run_checkSaleOneToOneMapped() throws Exception {
        // given
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("baseUrl", env.getProperty("openapi.request.url.APT_TRADE"))
                .addString("serviceKey", env.getProperty("openapi.request.serviceKey"))
                .addString("bjdCode", "11110")
                .addString("realEstateType", "APT_TRADE")
                .toJobParameters();

        // when
        jobLauncherTestUtils.launchJob(jobParameters);
        // then
        for (RealEstate realEstate : realEstateRepository.findAll()) {
            Assertions.assertTrue(saleRepository.existsById(realEstate.getId()));
        }
    }


}
