package by.smirnov.guitarstoreproject.repository;

import by.smirnov.guitarstoreproject.domain.Instock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface InstockRepository extends
        CrudRepository<Instock, Long>,
        JpaRepository<Instock, Long> {}
