package by.smirnov.guitarshopproject.service;

import by.smirnov.guitarshopproject.model.User;
import by.smirnov.guitarshopproject.repository.user.HibernateUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final HibernateUserRepo hibernateUserRepo;

    public User findById(Long id) {
        return hibernateUserRepo.findById(id);
    }

    public List<User> findAll() {
        return hibernateUserRepo.findAll();
    }

    public List<User> findAll(int limit, int offset) {
        return hibernateUserRepo.findAll(limit, offset);
    }

    public void create(User object) {
        hibernateUserRepo.create(object);
    }

    public User update(User user) {
        return hibernateUserRepo.update(user);
    }

    public void delete(Long id) {
        hibernateUserRepo.delete(id);
    }

    public Map<String, Object> getUserStats() {
        return hibernateUserRepo.getUserStats();
    }

    public List<User> showDeletedUsers() {
        return hibernateUserRepo.showDeletedUsers();
    }
}
