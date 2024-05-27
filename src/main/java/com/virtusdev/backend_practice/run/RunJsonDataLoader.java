package com.virtusdev.backend_practice.run;

import java.io.InputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.asm.TypeReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RunJsonDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(RunJsonDataLoader.class);
    private final JdbcClientRunRepository runRepository;
    private final ObjectMapper objectMapper;

    public RunJsonDataLoader(JdbcClientRunRepository runRepository, ObjectMapper objectMapper){
        this.runRepository = runRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception{
        if(runRepository.count() == 0){
            try(InputStream InputStream = TypeReference.class.getResourceAsStream("/data/runs.json")){
                Runs allRuns = objectMapper.readValue(InputStream, Runs.class);
                log.info("Reading {} runs from JSON data and saving to database.", allRuns.runs().size());
                runRepository.saveAll(allRuns.runs());
            }catch(IOException e){
                throw new RuntimeException("Failed to read JSON data", e);
            }
        }else {
            log.info("Not loading Runs from JSON data because the collection contains data.");
        }
    }
}
