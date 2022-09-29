package by.smirnov.guitarstoreproject.repository;

import by.smirnov.guitarstoreproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long>, JpaRepository<User, Long>,
        PagingAndSortingRepository<User, Long> {

    @Query(value = "select p from User p where p.isDeleted = true order by p.id")
    public List<User> findByHQLQuery();

    public List<User> findByIsDeletedOrderById(boolean isDeleted);
}
