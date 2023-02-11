package com.example.sidepot.global.config;

import com.example.sidepot.global.Path;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@RequiredArgsConstructor
@EnableWebMvc
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private static final Integer JACKSON_CONVERTER_SEQ = 0;
    private static final String APP_DATE_TIME_FORMAT = "yyyy.MM.dd.HH.mm.ss";
    private static final String APP_DATE_FORMAT = "yyyy.MM.dd";


    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
                .serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(APP_DATE_TIME_FORMAT)))
                .serializerByType(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(APP_DATE_FORMAT)))
                .build();
        converters.add(JACKSON_CONVERTER_SEQ, new MappingJackson2HttpMessageConverter(objectMapper));
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String s = "file:///";
        String d = new File("").getAbsolutePath();
        registry.addResourceHandler(Path.REST_BASE_PATH + "/profile/**")
                .addResourceLocations(s + "C:/Users/fullb/Desktop/api/profile/");
        registry.addResourceHandler(Path.REST_BASE_PATH + "/contract/**")
                .addResourceLocations(s + d + "contract/");
    }
}
