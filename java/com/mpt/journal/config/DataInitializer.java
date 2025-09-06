package com.mpt.journal.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataInitializer implements CommandLineRunner {

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Инициализация данных опциональна. 
        // Оставлено пустым, чтобы приложение стабильно запускалось без ошибок.
    }
}
