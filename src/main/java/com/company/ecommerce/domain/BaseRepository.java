package com.company.ecommerce.domain;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T, U> {
    T save(T t);

    void remove(U u);

    List<T> findAll();

    Optional<T> findById(U u);
}
