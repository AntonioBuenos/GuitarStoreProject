package by.smirnov.guitarstoreproject.repository;

import by.smirnov.guitarstoreproject.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends
        CrudRepository<User, Long>,
        JpaRepository<User, Long> {

    Page<User> findByIsDeleted(Pageable pageable, boolean isDeleted);

    Optional<User> findByLogin(String username);

    @Query("SELECT u FROM User u WHERE u.verificationCode = ?1")
    User findByVerificationCode(String code);
}
