package by.smirnov.guitarstoreproject.service;

import by.smirnov.guitarstoreproject.domain.User;
import by.smirnov.guitarstoreproject.exception.NoSuchEntityException;
import by.smirnov.guitarstoreproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository repository;

    @Override
    public User findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public User findByLogin(String login){
        return repository.findByLogin(login).orElse(null);
    }

    @Override
    public List<User> findAll(Pageable pageable) {
        return repository
                .findByIsDeleted(pageable, false)
                .getContent();
    }

    @Transactional
    @Override
    public User update(User toBeUpdated) {
        return repository.save(toBeUpdated);
    }

    @Transactional
    @Override
    public User delete(Long id) {
        User toBeDeleted = repository
                .findById(id)
                .orElseThrow(NoSuchEntityException::new);
        toBeDeleted.setIsDeleted(true);
        toBeDeleted.setTerminationDate(Timestamp.valueOf(LocalDateTime.now()));
        return repository.save(toBeDeleted);
    }

    @Transactional
    @Override
    public void hardDelete(Long id){
        repository.deleteById(id);
    }

    @Override
    public Page<User> showDeletedUsers(Pageable pageable) {
        return repository.findByIsDeleted(pageable, true);
    }
}
