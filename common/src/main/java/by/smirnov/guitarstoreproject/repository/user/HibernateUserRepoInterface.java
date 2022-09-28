package by.smirnov.guitarstoreproject.repository.user;

import by.smirnov.guitarstoreproject.model.User;
import by.smirnov.guitarstoreproject.repository.CRUDRepository;

import java.util.List;
import java.util.Map;

public interface HibernateUserRepoInterface extends CRUDRepository<Long, User> {

    Map<String, Object> getUserStats();
    public List<User> showDeletedUsers();
}
