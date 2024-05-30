package com.virtusdev.backend_practice.run;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class RunService {

    private final RunRepository runRepository;

    @Autowired
    public RunService(RunRepository runRepository) {
        this.runRepository = runRepository;
    }

    public List<Run> findAll() {
        return runRepository.findAll();
    }

    public Optional<Run> findById(Integer id) {
        return runRepository.findById(id);
    }

    public void create(Run run) {
        runRepository.create(run);
    }

    public void update(Run run, Integer id) {
        runRepository.update(run, id);
    }

    public void delete(Integer id) {
        runRepository.delete(id);
    }

    public List<Run> findByLocation(String location) {
        return runRepository.findByLocation(location);
    }
}
