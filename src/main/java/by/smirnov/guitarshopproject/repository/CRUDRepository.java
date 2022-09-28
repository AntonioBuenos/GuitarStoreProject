package by.smirnov.guitarshopproject.repository;

import java.util.List;
import java.util.Optional;

public interface CRUDRepository <K, T>{
    int DEFAULT_FIND_ALL_LIMIT = 25;
    int DEFAULT_FIND_ALL_OFFSET = 0;
    T findById(K id);
    Optional<T> findOne(K id);
    List<T> findAll();
    List<T> findAll(int limit, int offset);
    void create(T object);
    T update(T object);
    void delete(K id);
}
