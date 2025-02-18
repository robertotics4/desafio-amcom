package com.amcom.desafiotecnicoamcom.src.infra.contract;

public interface IProducer {
    void produce(String topic, String key, String message);
}
