package com.virtusdev.backend_practice.run;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/runs")
public class RunController {

    private final RunService runService;

    @Autowired
    public RunController(RunService runService) {
        this.runService = runService;
    }

    @GetMapping
    public ResponseEntity<List<Run>> findAll() {
        List<Run> runs = runService.findAll();
        return ResponseEntity.ok(runs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Run> findById(@PathVariable Integer id) {
        Optional<Run> run = runService.findById(id);
        if (run.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Run not found.");
        }
        return ResponseEntity.ok(run.get());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody Run run) {
        runService.create(run);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody Run run, @PathVariable Integer id) {
        runService.update(run, id);
        return ResponseEntity.noContent().build();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        runService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/location")
    public ResponseEntity<List<Run>> findByLocation(@RequestParam String location) {
        List<Run> runs = runService.findByLocation(location);
        return ResponseEntity.ok(runs);
    }
}
