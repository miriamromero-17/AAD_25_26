package com.example.add;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class AddApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AddApplication.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("Iniciando aplicación Act_1...");
        new Act_1().iniciar();
        log.info("Aplicación finalizada.");
    }
}
