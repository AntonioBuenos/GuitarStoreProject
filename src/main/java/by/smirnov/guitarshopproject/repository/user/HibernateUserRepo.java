package by.smirnov.guitarshopproject.repository.user;

import by.smirnov.guitarshopproject.model.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Primary
@RequiredArgsConstructor
public class HibernateUserRepo implements HibernateUserRepoInterface {

    private final SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    @Override
    public User findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        // в чем отличие от sessionFactory.openSession()?
        return session.get(User.class, id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findOne(Long id) {
        return Optional.of(findById(id));
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        return findAll(DEFAULT_FIND_ALL_LIMIT, DEFAULT_FIND_ALL_OFFSET);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll(int limit, int offset) {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select p from User p where p.isDeleted = false " +
                        "order by p.id", User.class)
                .getResultList();

/*        return session.createQuery("select p from User p where p.isDeleted = false " +
                        "order by p.id limit " + limit + " offset " + offset, User.class)
                .getResultList();*/
    }

    @Transactional
    @Override
    public void create(User object) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(object);
    }

    @Transactional
    @Override
    public User update(User user) {
        Session session = sessionFactory.getCurrentSession();
        User personToBeUpdated = session.get(User.class, user.getId());
        personToBeUpdated.setFirstName(user.getFirstName());
        personToBeUpdated.setLastName(user.getLastName());
        personToBeUpdated.setRole(user.getRole());
        personToBeUpdated.setModificationDate(Timestamp.valueOf(LocalDateTime.now()));
        personToBeUpdated.setLogin(user.getLogin());
        personToBeUpdated.setPassword(user.getPassword());
        return personToBeUpdated;
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        User personToBeDeleted = session.get(User.class, id);
        personToBeDeleted.setDeleted(true);
        personToBeDeleted.setTerminationDate(Timestamp.valueOf(LocalDateTime.now()));
    }

    @Transactional
    @Override
    public Map<String, Object> getUserStats() {
        return null;
    }

    @Transactional
    @Override
    public List<User> showDeletedUsers() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select p from User p where p.isDeleted = true order by p.id", User.class)
                .getResultList();
    }
}
