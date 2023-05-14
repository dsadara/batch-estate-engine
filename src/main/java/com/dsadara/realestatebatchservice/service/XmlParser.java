package com.dsadara.realestatebatchservice.service;

import com.dsadara.realestatebatchservice.dto.AptRentDto;
import org.jdom2.Element;

import java.util.List;

public class XmlParser {
    public static AptRentDto transferXmlToParser(Element item) {

        AptRentDto.AptRentDtoBuilder builder = AptRentDto.builder();

        List<Element> children = item.getChildren();
        for (Element child : children) {
            mappingFromItemToParser(builder, child);
        }

        return builder.build();

    }

    private static void mappingFromItemToParser(AptRentDto.AptRentDtoBuilder builder, Element item) {

        String value = item.getContent(0).getValue().trim();
        String name = item.getName();

        if(value.equals("")){
            return;
        }

        if(name.equals("갱신요구권사용")){
            builder.requestRenewalRight(value);
        }

        if(name.equals("건축년도")){
            builder.constructYear(value);
        }

        if(name.equals("계약구분")){
            builder.contractType(value);
        }

        if(name.equals("계약기간")){
            builder.contractPeriod(value);
        }

        if(name.equals("년")){
            builder.contractYear(value);
        }

        if(name.equals("법정동")){
            builder.legalDong(value);
        }

        if(name.equals("보증금액")){
            builder.deposit(value);
        }

        if(name.equals("아파트")){
            builder.name(value);
        }

        if(name.equals("월")){
            builder.month(value);
        }

        if(name.equals("월세금액")){
            builder.monthlyRent(value);
        }

        if(name.equals("일")){
            builder.day(value);
        }

        if(name.equals("전용면적")){
            builder.jeonYongArea(value);
        }

        if(name.equals("종전계약보증금")){
            builder.depositBefore(value);
        }

        if(name.equals("종전계약월세")){
            builder.monthlyRentBefore(value);
        }

        if(name.equals("지번")){
            builder.parcelNumber(value);
        }

        if(name.equals("지역코드")){
            builder.regionCode(value);
        }

        if(name.equals("층")){
            builder.floor(value);
        }
    }
}
