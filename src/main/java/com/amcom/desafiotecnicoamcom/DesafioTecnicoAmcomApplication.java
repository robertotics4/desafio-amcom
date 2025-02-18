package com.amcom.desafiotecnicoamcom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.TimeZone;

@SpringBootApplication
@EnableTransactionManagement
public class DesafioTecnicoAmcomApplication {
    public static void main(String[] args) {

        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
        SpringApplication.run(DesafioTecnicoAmcomApplication.class, args);
    }
}
