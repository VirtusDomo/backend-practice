package com.virtusdev.backend_practice.common;

import java.util.List;

public interface GenericRepository<T> {
    List<T> findAll();
    void saveAll(List<T> entities);
    int count();
}
