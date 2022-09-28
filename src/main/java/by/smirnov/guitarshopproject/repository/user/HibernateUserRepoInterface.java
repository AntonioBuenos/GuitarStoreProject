package by.smirnov.guitarshopproject.repository.user;

import by.smirnov.guitarshopproject.model.User;
import by.smirnov.guitarshopproject.repository.CRUDRepository;

import java.util.List;
import java.util.Map;

public interface HibernateUserRepoInterface extends CRUDRepository<Long, User> {

    Map<String, Object> getUserStats();
    public List<User> showDeletedUsers();
}
