package by.smirnov.guitarstoreproject.service;

import by.smirnov.guitarstoreproject.domain.User;
import by.smirnov.guitarstoreproject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private static final long ID = 1L;
    private static final String LOGIN = "login";
    private static final User testUser = User.builder()
            .id(ID)
            .address("City")
            .creationDate(Timestamp.valueOf(LocalDateTime.of(LocalDate.of(2023,1,1), LocalTime.MIN)))
            .email("test@test.by")
            .firstName("test")
            .lastName("test")
            .login("test")
            .password("test")
            .isEnabled(true)
            .isDeleted(false)
            .build();

    @Captor
    ArgumentCaptor<User> userCaptor;

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
        final Pageable pageable = mock(Pageable.class);
        List<User> users = new ArrayList<>();
        users.add(mock(User.class));
        users.add(mock(User.class));
        Page<User> page = new PageImpl<>(users);
        doReturn(page).when(repository).findByIsDeleted(pageable, false);

        List<User> actual = userService.findAll(pageable);

        assertThat(users).isEqualTo(actual);
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
        doReturn(Optional.of(testUser)).when(repository).findById(testUser.getId());
        userService.delete(testUser.getId());

        verify(repository).save(userCaptor.capture());
        User value = userCaptor.getValue();
        assertTrue(value.getIsDeleted());
    }

    @Test
    void hardDeleteShouldCallRepository() {
        userService.hardDelete(ID);

        verify(repository).deleteById(ID);
    }

    @Test
    void showDeletedUsers() {
        final Pageable pageable = mock(Pageable.class);
        List<User> users = new ArrayList<>();
        users.add(mock(User.class));
        users.add(mock(User.class));
        Page<User> page = new PageImpl<>(users);
        doReturn(page).when(repository).findByIsDeleted(pageable, true);

        Page<User> actual = userService.showDeletedUsers(pageable);

        assertThat(users).isEqualTo(actual.getContent());
    }
}
