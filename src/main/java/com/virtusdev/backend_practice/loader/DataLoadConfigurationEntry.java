package com.virtusdev.backend_practice.loader;

import com.virtusdev.backend_practice.common.GenericRepository;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class DataLoadConfigurationEntry<T> {

    private final GenericRepository<T> repository;
    private final String jsonFile;
    private final TypeReference<List<T>> typeReference;

    public DataLoadConfigurationEntry(GenericRepository<T> repository, String jsonFile, TypeReference<List<T>> typeReference) {
        this.repository = repository;
        this.jsonFile = jsonFile;
        this.typeReference = typeReference;
    }

    public GenericRepository<T> getRepository() {
        return repository;
    }

    public String getJsonFile() {
        return jsonFile;
    }

    public TypeReference<List<T>> getTypeReference() {
        return typeReference;
    }
}
