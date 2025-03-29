package org.example.dao;

import java.util.List;

public interface Dao<T> {

    List<T> getAll();

    T getById(Long id);

    void save(T entity);

    void update(T entity);

    void delete(Long id);


}
