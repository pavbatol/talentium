package com.pavbatol.talentium.app.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pavbatol.talentium.reference.country.model.Country;
import com.pavbatol.talentium.reference.country.repository.CountryRepository;
import com.pavbatol.talentium.reference.highschool.model.HighSchool;
import com.pavbatol.talentium.reference.highschool.model.SchoolType;
import com.pavbatol.talentium.reference.highschool.repository.HighSchoolRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileDataLoader {

    public static final String COMMERCIAL = "НЕГОСУДАРСТВЕННЫЙ";
    public static final String STATE = "ГОСУДАРСТВЕННЫЙ";
    public static final String MUNICIPAL = "МУНИЦИПАЛЬНЫЙ";

    @Value(value = "${app.data.file-path.countries}")
    private String countriesFilePath;
    @Value(value = "${app.data.file-path.high-schools}")
    private String highSchoolsFilePath;
    @Value(value = "${app.data.file-path.cities}")
    private String citiesFilePath;

    private final ObjectMapper objectMapper;
    private final CountryRepository countryRepository;
    private final HighSchoolRepository highSchoolRepository;

    public void loadCountries() {
        String correctModulePath = getModulePath();
        Path path = Path.of(correctModulePath, countriesFilePath);
        log.debug("-Path to loud Countries: {}", path);

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
                                .setNameEn(parts.length < 2 || parts[1].trim().isEmpty() ? parts[0].trim() : parts[1].trim())
                                .setNameRu(parts.length < 3 || parts[2].trim().isEmpty() ? parts[0].trim() : parts[2].trim());
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

    public void loadHighSchools() {
        String correctModulePath = getModulePath();
        Path path = Path.of(correctModulePath, highSchoolsFilePath);
        log.debug("-Path to loud High-schools: {}", path);

        try (InputStream inp = new FileInputStream(path.toString())) {
            DataFormatter dataFormatter = new DataFormatter();
            Workbook workbook = WorkbookFactory.create(inp);
            Sheet sheet = workbook.getSheetAt(0);
            List<HighSchool> schools = StreamSupport.stream(sheet.spliterator(), false)
                    .skip(1)
                    .filter(row -> row.getCell(0) != null)
                    .map(row -> {
                        String name = row.getCell(0).getStringCellValue().trim();
                        int indexEnd = name.indexOf("_x00");
                        name = indexEnd == -1 ? name : name.substring(0, indexEnd);
                        return new HighSchool()
                                .setName(name)
                                .setType(row.getCell(1) != null ? getSchoolType(row.getCell(1).getStringCellValue()) : SchoolType.UNKNOWN)
                                .setRegion(row.getCell(2) != null ? row.getCell(2).getStringCellValue().trim() : null)
                                .setCity(row.getCell(3) != null ? row.getCell(3).getStringCellValue().trim() : null)
                                .setPhone(row.getCell(4) != null ? dataFormatter.formatCellValue(row.getCell(4)).trim() : null)
                                .setWebsite(row.getCell(7) != null ? row.getCell(7).getStringCellValue().trim() : null);
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toMap(HighSchool::getName, Function.identity(), (a, b) -> a))
                    .values().stream()
                    .toList();

            List<String> existingNames = highSchoolRepository.findAllByNameIn(schools.stream()
                    .map(HighSchool::getName)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet()));

            List<HighSchool> schoolsToSave = schools.stream()
                    .filter(Objects::nonNull)
                    .filter(highSchool -> highSchool.getName() != null)
                    .filter(highSchool -> !existingNames.contains(highSchool.getName()))
                    .toList();

            if (!schoolsToSave.isEmpty()) {
                highSchoolRepository.saveAll(schoolsToSave);
                log.debug("-The directory of schools has been loaded in the amount of {}", schoolsToSave.size());
            } else {
                log.debug("-No new schools to load");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadCities(String... filePaths) {
        List<String> collect = Arrays.stream(filePaths)
                .map(filePath -> {
                    String correctModulePath = getModulePath();
                    Path path = Path.of(correctModulePath, filePath.trim());
                    log.debug("-Path to loud Cities: {}", path);

                    JsonFactory jsonFactory = objectMapper.getFactory();
                    try (JsonParser jsonParser = jsonFactory.createParser(new FileInputStream(path.toString()))) {

                        StringBuilder builder = new StringBuilder();

                        while (jsonParser.nextToken() != null) {
                            if (jsonParser.getCurrentToken() == JsonToken.FIELD_NAME && "features".equals(jsonParser.currentName())) {
                                String featureId;
                                String featureCountry;
                                String featureRegion;
                                String featureDistrict;
                                String featureName;
                                String featureOfficialStatus;
                                String featureIsInCountryCode;
                                double featureLongitude = .0;
                                double featureLatitude = .0;

                                int count = 0;
                                while (jsonParser.nextToken() != JsonToken.END_ARRAY && count < 10) {
//                                while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                                    count++;
                                    while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                                        if (jsonParser.currentToken() != JsonToken.FIELD_NAME) {
                                            continue;
                                        }
                                        String subFieldName = jsonParser.getCurrentName();
                                        jsonParser.nextToken();
                                        switch (subFieldName) {
                                            case "properties":
                                                while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                                                    String propsFieldName = jsonParser.getCurrentName();
                                                    jsonParser.nextToken();
                                                    switch (propsFieldName) {
                                                        case "@id":
                                                            featureId = jsonParser.getValueAsString();
                                                            builder.append("@id=").append(featureId);
                                                            break;
                                                        case "addr:country":
                                                            featureCountry = jsonParser.getValueAsString();
                                                            builder.append(", country=").append(featureCountry);
                                                            break;
                                                        case "addr:region":
                                                            featureRegion = jsonParser.getValueAsString();
                                                            builder.append(", region=").append(featureRegion);
                                                            break;
                                                        case "addr:district":
                                                            featureDistrict = jsonParser.getValueAsString();
                                                            builder.append(", district=").append(featureDistrict);
                                                            break;
                                                        case "name":
                                                            featureName = jsonParser.getValueAsString();
                                                            builder.append(", name=").append(featureName);
                                                            break;
                                                        case "official_status":
                                                            featureOfficialStatus = jsonParser.getValueAsString();
                                                            builder.append(", official_status=").append(featureOfficialStatus);
                                                            break;
                                                        case "is_in:country_code":
                                                            featureIsInCountryCode = jsonParser.getValueAsString();
                                                            builder.append(", is_in:country_code=").append(featureIsInCountryCode);
                                                            break;
                                                    }
                                                }
                                                break;
                                            case "geometry":
                                                while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                                                    String geomFieldName = jsonParser.getCurrentName();
                                                    jsonParser.nextToken();
                                                    if ("coordinates".equals(geomFieldName)) {
                                                        int i = 0;
                                                        while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                                                            double coord = jsonParser.getDoubleValue();
                                                            if (i == 0) {
                                                                featureLongitude = coord;
                                                            } else {
                                                                featureLatitude = coord;
                                                            }
                                                            i++;
                                                        }
                                                        builder.append(", longitude=").append(featureLongitude)
                                                                .append(", latitude=").append(featureLatitude)
                                                                .append("\n");
                                                    }
                                                }
                                                break;
                                        }
                                    }
                                }
                                log.debug("-Loaded city number: {} from file: {}", count, path);
                            }
                        }

                        return builder.toString();

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();

        collect.forEach(System.out::println);

    }

    private SchoolType getSchoolType(@NonNull String typeStr) {
        typeStr = typeStr.trim().toUpperCase();
        if (typeStr.startsWith(COMMERCIAL)) {
            return SchoolType.COMMERCIAL;
        } else if (typeStr.startsWith(STATE) || typeStr.startsWith(MUNICIPAL)) {
            return SchoolType.STATE;
        } else {
            return SchoolType.UNKNOWN;
        }
    }

    private String getModulePath() {
        String projectPath = System.getProperty("user.dir");
        String modulePath = Objects.requireNonNull(getClass().getClassLoader().getResource("")).getPath();
        int indexStart = projectPath.length() + 1;
        int indexEnd = modulePath.indexOf(System.getProperty("file.separator"), indexStart);
        return indexEnd == -1 ? modulePath : modulePath.substring(indexStart, indexEnd);
    }

    public void run() {
        loadCountries();
        loadHighSchools();
        loadCities(citiesFilePath.split(","));
    }
}
