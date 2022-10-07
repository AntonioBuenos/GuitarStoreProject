package by.smirnov.guitarstoreproject.repository;

import by.smirnov.guitarstoreproject.model.Genre;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

@Cacheable("genres")
public interface GenreRepository extends CrudRepository<Genre, Long>,
        JpaRepository<Genre, Long>,
        PagingAndSortingRepository<Genre, Long> {
}
