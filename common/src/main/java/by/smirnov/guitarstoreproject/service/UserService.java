package by.smirnov.guitarstoreproject.service;

import by.smirnov.guitarstoreproject.domain.User;
import by.smirnov.guitarstoreproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public User findByLogin(String login){
        return repository.findByLogin(login).orElse(null);
    }

    public List<User> findAll(int pageNumber, int pageSize) {
        return repository
                .findByIsDeletedOrderById(PageRequest.of(pageNumber, pageSize), false)
                .getContent();
    }

    @Transactional
    public User update(User toBeUpdated) {
        return repository.save(toBeUpdated);
    }

    @Transactional
    public User delete(Long id) {
        User toBeDeleted = repository.findById(id).orElse(null);
        if(Objects.isNull(toBeDeleted)) return null;
        toBeDeleted.setIsDeleted(true);
        toBeDeleted.setTerminationDate(Timestamp.valueOf(LocalDateTime.now()));
        return repository.save(toBeDeleted);
    }

    @Transactional
    public void hardDelete(Long id){
        repository.deleteById(id);
    }

    public Page<User> showDeletedUsers(int pageNumber, int pageSize) {
        return repository.findByIsDeletedOrderById(
                PageRequest.of(pageNumber, pageSize),
                true
        );
    }
}
