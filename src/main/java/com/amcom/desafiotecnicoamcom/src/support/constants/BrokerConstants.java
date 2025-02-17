package com.amcom.desafiotecnicoamcom.src.support.constants;

public class BrokerConstants {
    private BrokerConstants() {}

    public static final class Topics {
        private Topics(){}

        public static final String EXTERNAL_AVAILABLE_ORDER_TOPIC = "amcom.external.available_order";
        public static final String EXTERNAL_PROCESSED_ORDER_TOPIC = "amcom.external.processed_order";
    }
}
