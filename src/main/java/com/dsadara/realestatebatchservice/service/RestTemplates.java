package com.dsadara.realestatebatchservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
public class RestTemplates {
    private final RestTemplate restTemplate;

    public RestTemplates(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> getResponse(String baseUrl, MultiValueMap<String, String> queryParams) {
        URI uri = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParams(queryParams)
                .build(true)
                .toUri();

        return restTemplate.getForEntity(uri, String.class);
    }

}
