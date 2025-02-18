package com.amcom.desafiotecnicoamcom.src.config.jackson;

public interface JsonConverter {
    String getJsonOfObject(Object object);

    <T> T getObject(String jsonStr, Class<T> clazz);
}
