package com.dsadara.realestatebatchservice.reader;

import com.dsadara.realestatebatchservice.dto.RealEstateDto;
import com.dsadara.realestatebatchservice.service.ApiRequester;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;

import java.util.LinkedList;
import java.util.List;

@Slf4j
public class ApiItemReader implements ItemReader<RealEstateDto> {

    private final String baseUrl;
    private final String serviceKey;
    private final ApiRequester apiRequester;
    private final List<RealEstateDto> items = new LinkedList<>();

    public ApiItemReader(String baseUrl, String serviceKey, ApiRequester apiRequester) throws Exception {
        this.baseUrl = baseUrl;
        this.serviceKey = serviceKey;
        this.apiRequester = apiRequester;
    }

    @Override
    public RealEstateDto read() {
        if (items.isEmpty()) {
            return null;
        } else {
            return items.remove(0);
        }
    }

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) throws Exception {
        String bjdCode = stepExecution.getExecutionContext().getString("bjdCode");
        String contractYMD = stepExecution.getExecutionContext().getString("contractYMD");
        items.addAll(apiRequester.fetchData(baseUrl, serviceKey, bjdCode, contractYMD));
    }

}
