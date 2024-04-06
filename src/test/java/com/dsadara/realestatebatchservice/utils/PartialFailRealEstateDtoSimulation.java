package com.dsadara.realestatebatchservice.utils;

import com.dsadara.realestatebatchservice.dto.RealEstateDto;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.concurrent.atomic.AtomicInteger;

public class PartialFailRealEstateDtoSimulation implements Answer<RealEstateDto> {
    private final AtomicInteger callCount = new AtomicInteger(0);

    @Override
    public RealEstateDto answer(InvocationOnMock invocation) throws Throwable {
        int count = callCount.incrementAndGet();
        // 49번째 step까지는 실패
        if (count <= 49) {
            throw new Exception("test Exception");
        }
        // 50번째 step은 성공
        if (count == 50) {
            return new RealEstateDto();
        }
        // 이후에 null 반환하여 종료
        return null;
    }
}
