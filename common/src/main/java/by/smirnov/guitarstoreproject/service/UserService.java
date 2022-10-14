package by.smirnov.guitarstoreproject.service;

import by.smirnov.guitarstoreproject.model.User;
import by.smirnov.guitarstoreproject.model.enums.Role;
import by.smirnov.guitarstoreproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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

    public List<User> findAll() {
        return repository.findByIsDeletedOrderById(false);
    }

    public List<User> findAll(int limit, int offset) {
        return repository.findByIsDeletedOrderById(false);
    }

    public void create(User object) {
        object.setRole(Role.ROLE_CUSTOMER);
        object.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
        object.setIsDeleted(false);
        repository.save(object);
    }

    public User update(User toBeUpdated) {
        User old = repository.findById(toBeUpdated.getId()).orElse(null);
        toBeUpdated.setCreationDate(old.getCreationDate());
        toBeUpdated.setModificationDate(Timestamp.valueOf(LocalDateTime.now()));
        toBeUpdated.setIsDeleted(old.getIsDeleted());
        toBeUpdated.setOrders(old.getOrders());
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

/*    public Map<String, Object> getUserStats() {
        return userRepo.getUserStats();
    }*/

    public List<User> showDeletedUsers() {
        return repository.findByIsDeletedOrderById(true);
    }
}
