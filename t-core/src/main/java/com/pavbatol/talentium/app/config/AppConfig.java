package com.pavbatol.talentium.app.config;

import com.pavbatol.talentium.app.util.FileDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.pavbatol")
public class AppConfig {

    @Bean
    public CommandLineRunner dataLoader(FileDataLoader loader) {
        return args -> loader.run();
    }
}
