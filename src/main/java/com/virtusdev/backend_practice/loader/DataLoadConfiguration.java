package com.virtusdev.backend_practice.loader;

import com.virtusdev.backend_practice.common.GenericRepository;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class DataLoadConfiguration<T> {

    private final GenericRepository<T> repository;
    private final String fileName;
    private final TypeReference<List<T>> typeReference;

    public DataLoadConfiguration(GenericRepository<T> repository, String fileName, TypeReference<List<T>> typeReference) {
        this.repository = repository;
        this.fileName = fileName;
        this.typeReference = typeReference;
    }

    public GenericRepository<T> getRepository() {
        return repository;
    }

    public String getFileName() {
        return fileName;
    }

    public TypeReference<List<T>> getTypeReference() {
        return typeReference;
    }
}
