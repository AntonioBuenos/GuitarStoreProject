package by.smirnov.guitarstoreproject.repository;

import by.smirnov.guitarstoreproject.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface GenreRepository extends CrudRepository<Genre, Long>,
        JpaRepository<Genre, Long>,
        PagingAndSortingRepository<Genre, Long> {

    public List<Genre> findByIsDeletedOrderById(boolean isDeleted);
}
