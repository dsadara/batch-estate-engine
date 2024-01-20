package com.dsadara.realestatebatchservice.job;

import com.dsadara.realestatebatchservice.dto.RealEstateDto;
import com.dsadara.realestatebatchservice.service.RequestData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;

import java.util.LinkedList;
import java.util.List;

@Slf4j
public class ApiItemReader implements ItemReader<RealEstateDto> {

    private final String baseUrl;
    private final String serviceKey;
    private final String bjdCode;
    private final String contractYMD;
    private final List<RealEstateDto> items = new LinkedList<>();

    public ApiItemReader(String baseUrl, String serviceKey, String bjdCode, String contractYMD, RequestData requestData) throws Exception {
        this.baseUrl = baseUrl;
        this.serviceKey = serviceKey;
        this.bjdCode = bjdCode;
        this.contractYMD = contractYMD;
        this.items.addAll(requestData.requestData(baseUrl, bjdCode, contractYMD, serviceKey));
    }

    @Override
    public RealEstateDto read() {
        if (items.isEmpty()) {
            return null;
        } else {
            return items.remove(0);
        }
    }

}
