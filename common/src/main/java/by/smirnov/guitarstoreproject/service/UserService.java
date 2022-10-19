package by.smirnov.guitarstoreproject.service;

import by.smirnov.guitarstoreproject.model.User;
import by.smirnov.guitarstoreproject.model.enums.Role;
import by.smirnov.guitarstoreproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

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
        return repository.findByIsDeletedOrderById(PageRequest.of(pageNumber, pageSize), false).getContent();
    }

    public User update(User toBeUpdated) {
        return repository.save(toBeUpdated);
    }

    public void delete(Long id) {
        User toBeDeleted = repository.findById(id).orElse(null);
        toBeDeleted.setIsDeleted(true);
        toBeDeleted.setTerminationDate(Timestamp.valueOf(LocalDateTime.now()));
        repository.save(toBeDeleted);
    }

    public void hardDelete(Long id){
        repository.deleteById(id);
    }

    public Page<User> showDeletedUsers(int pageNumber, int pageSize) {
        return repository.findByIsDeletedOrderById(PageRequest.of(pageNumber, pageSize), true);
    }
}
