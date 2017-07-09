package com.cargowhale.docker.config.web;

import com.cargowhale.docker.client.logs.LogStreamMessageConverter;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void extendMessageConverters(final List<HttpMessageConverter<?>> converters) {
        converters.add(new LogStreamMessageConverter());
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> builder.dateFormat(new ISO8601DateFormat());
    }
}
