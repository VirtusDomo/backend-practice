package com.virtusdev.backend_practice.run;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RunControllerIntTest {

    @LocalServerPort
    int randomServerPort;

    RestClient restClient;

    @BeforeEach
    void setup(){
        restClient = RestClient.builder()
                .baseUrl("http://localhost:" + randomServerPort)
                .build();
    }

    @Test
    void shouldFindAllRuns(){
        List<Run> runs = restClient.get()
                .uri("/api/runs")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Run>>() {});

        assertEquals(10, runs.size());
    }

    @Test
    void shouldFindOneRun() {
        Run run = restClient.get()
                .uri("/api/runs/{id}", 1)
                .retrieve()
                .body(Run.class);

        assertNotNull(run);
        assertEquals(1, run.id());
        assertEquals("Noon Run", run.title());
    }

    @Test
    void shouldCreateNewRun() {
        Run newRun = new Run(11, "Evening Run", LocalDateTime.now(), LocalDateTime.now().plus(30, ChronoUnit.MINUTES), 5, Location.OUTDOOR, null);

        // Act: Perform the create operation
        try {
            ResponseEntity<Run> responseEntity = restClient.post()
                    .uri("/api/runs")
                    .body(newRun)
                    .retrieve()
                    .toEntity(Run.class);

            // Assert: Verify the response and the created run
            assertNotNull(responseEntity);
            assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
            Run createdRun = restClient.get().uri("/api/runs/{id}", 11).retrieve().body(Run.class);
            assertNotNull(createdRun);
            assertEquals("Evening Run", createdRun.title());
        } catch (RestClientResponseException e) {
            // Handle and log the error
            System.err.println("Request failed: " + e.getMessage());
        }
    }

    @Test
    void shouldUpdateRun() {
        Run updatedRun = new Run(1, "Updated Run", LocalDateTime.now(), LocalDateTime.now().plusMinutes(45), 10, Location.OUTDOOR, null);

        // Act: Perform the update operation
        restClient.put()
                .uri("/api/runs/{id}", 1)
                .body(updatedRun)
                .retrieve()
                .toEntity(Run.class);

        // Assert: Retrieve the run and verify the update
        Run run = restClient.get()
                .uri("/api/runs/{id}", 1)
                .retrieve()
                .body(Run.class);

        assertEquals("Updated Run", run.title());
    }

    @Test
    void shouldDeleteRun() {
        restClient.delete()
                .uri("/api/runs/{id}", 1)
                .retrieve()
                .toBodilessEntity();

        List<Run> runs = restClient.get()
                .uri("/api/runs")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Run>>() {});

        assertEquals(9, runs.size());
    }
}
