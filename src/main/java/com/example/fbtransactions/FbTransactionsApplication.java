package com.example.fbtransactions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;

@SpringBootApplication
public class FbTransactionsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FbTransactionsApplication.class, args);
        Locale.setDefault(new Locale("en", "US"));

    }

}
