package by.smirnov.guitarstoreproject.service;

import by.smirnov.guitarstoreproject.domain.User;
import by.smirnov.guitarstoreproject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private static final long ID = 1L;
    private static final String LOGIN = "login";

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository repository;

    @Test
    void findByIdShouldCallRepository() {
        final User user = mock(User.class);
        when(repository.findById(ID)).thenReturn(Optional.ofNullable(user));

        final User actual = userService.findById(ID);

        assertThat(actual).isEqualTo(user);
    }

    @Test
    void findByLoginShouldCallRepository() {
        final User user = mock(User.class);
        when(repository.findByLogin(LOGIN)).thenReturn(Optional.ofNullable(user));

        final User actual = userService.findByLogin(LOGIN);

        assertThat(actual).isEqualTo(user);
    }

    @Test
    void findAll() {
    }

    @Test
    void updateShouldCallRepository() {
        final User user = mock(User.class);
        when(repository.save(user)).thenReturn(user);

        final User actual = userService.update(user);

        assertThat(actual).isEqualTo(user);
    }

    @Test
    void delete() {
    }

    @Test
    void hardDeleteShouldCallRepository() {
        userService.hardDelete(ID);

        verify(repository).deleteById(ID);
    }

    @Test
    void showDeletedUsers() {
    }
}
