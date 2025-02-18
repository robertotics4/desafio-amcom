package com.amcom.desafiotecnicoamcom.src.config.jackson;

import com.amcom.desafiotecnicoamcom.src.domain.exception.JacksonException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;

@Configuration
public class JacksonConfig {
    @Bean
    @Primary
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
        return objectMapper;
    }

    @Bean
    @Primary
    public JsonConverter conversorJson(ObjectMapper objectMapper) {
        return new ObjectMapperConverter(objectMapper);
    }

    @RequiredArgsConstructor
    public static class ObjectMapperConverter implements JsonConverter {

        private final ObjectMapper objectMapper;

        public String getJsonOfObject(Object object) {
            try {
                return objectMapper.writeValueAsString(object);
            } catch (JsonProcessingException e) {
                throw new JacksonException("Falha ObjectMapper: " + e.getMessage());
            }
        }

        public <T> T getObject(String jsonStr, Class<T> clazz) {
            try {
                return objectMapper.readValue(jsonStr, clazz);
            } catch (IOException e) {
                throw new JacksonException("Falha ObjectMapper: " + e.getMessage());
            }
        }
    }
}
