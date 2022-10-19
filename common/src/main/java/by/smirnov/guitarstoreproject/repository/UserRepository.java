package by.smirnov.guitarstoreproject.repository;

import by.smirnov.guitarstoreproject.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long>, JpaRepository<User, Long> {

    public Page<User> findByIsDeletedOrderById(Pageable pageable, boolean isDeleted);

    Optional<User> findByLogin(String username);
}
