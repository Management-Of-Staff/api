package com.example.sidepot.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;


@RequiredArgsConstructor
@EnableWebMvc
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private static final Integer JACKSON_CONVERTER_SEQ = 0;
    private static final String APP_DATE_TIME_FORMAT = "yyyy.MM.dd-HH:mm:ss";
    private static final String APP_DATE_FORMAT = "yyyy.MM.dd";
    private static final String APP_TIME_FORMAT = "HH:mm:ss";

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder
                .json()
                .serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(APP_DATE_TIME_FORMAT)))
                .serializerByType(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(APP_DATE_FORMAT)))
                .serializerByType(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(APP_TIME_FORMAT)))
                .deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(APP_DATE_TIME_FORMAT)))
                .deserializerByType(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(APP_DATE_FORMAT)))
                .deserializerByType(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(APP_TIME_FORMAT)))
                .build();
        converters.add(JACKSON_CONVERTER_SEQ, new MappingJackson2HttpMessageConverter(objectMapper));
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry){
        registry.addResourceHandler("/daum_zip_code.html")
                .addResourceLocations("classpath:/templates/")
                .setCacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES));
    }
}
