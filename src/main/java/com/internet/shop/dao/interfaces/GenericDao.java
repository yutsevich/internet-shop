package com.internet.shop.dao.interfaces;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T, K> {
    T create(T item);

    Optional<T> get(K id);

    List<T> getAll();

    T update(T product);

    boolean deleteById(K id);

    boolean delete(T item);
}
