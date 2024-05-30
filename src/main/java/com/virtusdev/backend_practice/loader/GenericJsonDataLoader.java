package com.virtusdev.backend_practice.loader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtusdev.backend_practice.common.GenericRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public class GenericJsonDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(GenericJsonDataLoader.class);
    private final List<DataLoadConfiguration<?>> dataLoadConfigurations;
    private final ObjectMapper objectMapper;

    public GenericJsonDataLoader(List<DataLoadConfiguration<?>> dataLoadConfigurations, ObjectMapper objectMapper) {
        this.dataLoadConfigurations = dataLoadConfigurations;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        for (DataLoadConfiguration<?> config : dataLoadConfigurations) {
            loadData(config);
        }
    }

    private <T> void loadData(DataLoadConfiguration<T> config) {
        GenericRepository<T> repository = config.getRepository();
        if (repository.count() == 0) {
            try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/" + config.getFileName())) {
                List<T> entities = objectMapper.readValue(inputStream, config.getTypeReference());
                log.info("Reading {} entries from JSON data and saving to database.", entities.size());
                repository.saveAll(entities);
            } catch (Exception e) {
                throw new RuntimeException("Failed to read JSON data from " + config.getFileName(), e);
            }
        } else {
            log.info("Not loading {} from JSON data because the collection contains data.", config.getFileName());
        }
    }
}
