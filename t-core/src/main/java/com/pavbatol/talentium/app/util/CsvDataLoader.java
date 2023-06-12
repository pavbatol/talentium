package com.pavbatol.talentium.app.util;

import com.pavbatol.talentium.reference.country.model.Country;
import com.pavbatol.talentium.reference.country.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class CsvDataLoader {

    @Value(value = "${app.data.import.file.path.countries}")
    private String countriesFilePath;

    @Value(value = "${app.data.import.file.path.higher-eds}")
    private String higherEdsFilePath;

    private final CountryRepository countryRepository;

    public void loudCountries() {
        String projectPath = System.getProperty("user.dir");
        String modulePath = Objects.requireNonNull(getClass().getClassLoader().getResource("")).getPath();
        int indexStart = projectPath.length() + 1;
        int indexEnd = modulePath.indexOf(System.getProperty("file.separator"), indexStart);
        String correctModulePath = modulePath.substring(indexStart, indexEnd);

        Path path = Path.of(correctModulePath, countriesFilePath);
        log.debug("-Path to loud countries: {}", path);

        try (BufferedReader br = Files.newBufferedReader(path)) {
            List<Country> countries = new ArrayList<>();
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0] != null && !parts[0].trim().isEmpty()) {
                    String code = parts[0].trim();
                    if (!countryRepository.existsByCode(code)) {
                        Country country = new Country()
                                .setCode(code)
                                .setNameEn(parts[1] == null || parts[1].trim().isEmpty() ? parts[0].trim() : parts[1].trim())
                                .setNameRu(parts[2] == null || parts[2].trim().isEmpty() ? parts[0].trim() : parts[2].trim());
                        countries.add(country);
                    }
                }
            }
            if (!countries.isEmpty()) {
                countryRepository.saveAll(countries);
                log.debug("-The directory of countries has been loaded in the amount of {}", countries.size());
            } else {
                log.debug("-No new countries to load");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {
        loudCountries();
    }
}
