package com.dsadara.realestatebatchservice.config;

import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.web.client.AsyncRestTemplate;

@Configuration
public class AsyncRestTemplateConfig {

    @Bean
    public AsyncRestTemplate restTemplateAsync() {
        Netty4ClientHttpRequestFactory factory =
                new Netty4ClientHttpRequestFactory(new NioEventLoopGroup(1));
        return new AsyncRestTemplate(factory);
    }

}
