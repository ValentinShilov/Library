package com.egar.library.service;

import java.util.Collection;

public interface CRUDService<T>{
    T getById(Long id);

    Collection<T> findAll();

    void create(T t);

    void update(Long id, T t);
    default void update( T t){

    }

    void delete(Long id);

}
