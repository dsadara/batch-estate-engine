package com.dsadara.realestatebatchservice.service;

import com.dsadara.realestatebatchservice.dto.AptRentDto;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class OpenApiExplorer {
    public static List<AptRentDto> getAptRentData(String baseUrl, String regionCode, String contractYearMonthDay, String authenticationKey) throws IOException, JDOMException {

        String sb = baseUrl +
                "LAWD_CD=" + regionCode + "&" +
                "DEAL_YMD=" + contractYearMonthDay + "&" +
                "serviceKey=" + authenticationKey;

        URL url = new URL(sb);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();

        conn.setRequestProperty("Content-Type","application/xml");
        conn.setRequestMethod("GET");
        conn.connect();
        System.out.println("ResponseCode: " + conn.getResponseCode());

        SAXBuilder builder = new SAXBuilder();
        Document document = builder.build(conn.getInputStream());

        Element root = document.getRootElement();
        Element body = root.getChild("body");
        Element items = body.getChild("items");
        List<Element> item = items.getChildren("item");

        List<AptRentDto> aptRentDtos = new LinkedList<>();
        for (Element element : item) {
            aptRentDtos.add(XmlParser.transferXmlToParser(element));
        }

        return aptRentDtos;
    }

}
