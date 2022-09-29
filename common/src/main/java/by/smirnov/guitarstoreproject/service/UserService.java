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

    private final UserRepository userRepo;

    public User findById(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public Page<User> findAll(int limit, int offset) {
        return userRepo.findAll(PageRequest.of(offset, limit));
    }

    public void create(User object) {
        object.setRole(Role.ROLE_CUSTOMER);
        object.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
        object.setLogin("login");
        object.setPassword("password");
        userRepo.save(object);
    }

    public User update(User toBeUpdated) {
        User old = userRepo.getReferenceById(toBeUpdated.getId());
        toBeUpdated.setCreationDate(old.getCreationDate());
        toBeUpdated.setModificationDate(Timestamp.valueOf(LocalDateTime.now()));
        toBeUpdated.setLogin(old.getLogin());
        toBeUpdated.setPassword(old.getPassword());
        return userRepo.save(toBeUpdated);
    }

    public void delete(Long id) {
        User toBeDeleted = userRepo.findById(id).orElse(null);
        toBeDeleted.setDeleted(true);
        toBeDeleted.setTerminationDate(Timestamp.valueOf(LocalDateTime.now()));
        userRepo.save(toBeDeleted);
    }

    public void hardDelete(Long id){
        userRepo.deleteById(id);
    }

/*    public Map<String, Object> getUserStats() {
        return userRepo.getUserStats();
    }*/

    public List<User> showDeletedUsers() {
        return userRepo.findByHQLQuery();
    }
}
