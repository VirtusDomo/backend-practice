package com.virtusdev.backend_practice.run;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class RunServiceTest {

    @Mock
    private RunRepository runRepository;

    @InjectMocks
    private RunService runService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllRuns() {
        Run run = new Run(1, "Morning Run", LocalDateTime.now(), LocalDateTime.now().plusMinutes(45), 5, Location.OUTDOOR,null);
        when(runRepository.findAll()).thenReturn(List.of(run));

        List<Run> runs = runService.findAll();

        assertThat(runs).hasSize(1);
        verify(runRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnRunById() {
        Run run = new Run(1, "Morning Run", LocalDateTime.now(), LocalDateTime.now().plusMinutes(40), 5, Location.OUTDOOR, null);
        when(runRepository.findById(1)).thenReturn(Optional.of(run));

        Optional<Run> result = runService.findById(1);

        assertThat(result).isPresent();
        assertThat(result.get().id()).isEqualTo(1);
        verify(runRepository, times(1)).findById(1);
    }

    @Test
    void shouldCreateRun() {
        Run run = new Run(1, "Morning Run", LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), 5, Location.OUTDOOR, null);

        runService.create(run);

        verify(runRepository, times(1)).create(run);
    }

    @Test
    void shouldUpdateRun() {
        Run run = new Run(1, "Morning Run", LocalDateTime.now(), LocalDateTime.now().plusMinutes(45), 5, Location.OUTDOOR, null);

        runService.update(run, 1);

        verify(runRepository, times(1)).update(run, 1);
    }

    @Test
    void shouldDeleteRun() {
        runService.delete(1);

        verify(runRepository, times(1)).delete(1);
    }

    @Test
    void shouldReturnRunsByLocation() {
        Run run = new Run(1, "Morning Run", LocalDateTime.now(), LocalDateTime.now().plusMinutes(45), 5, Location.OUTDOOR,null);
        when(runRepository.findByLocation("OUTDOOR")).thenReturn(List.of(run));

        List<Run> runs = runService.findByLocation("OUTDOOR");

        assertThat(runs).hasSize(1);
        assertThat(runs.get(0).location().toString()).isEqualTo("OUTDOOR");
        verify(runRepository, times(1)).findByLocation("OUTDOOR");
    }
}
