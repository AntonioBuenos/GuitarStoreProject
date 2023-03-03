package by.smirnov.guitarstoreproject.service;

import by.smirnov.guitarstoreproject.domain.User;
import by.smirnov.guitarstoreproject.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
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

import java.util.List;
import java.util.Optional;

import static by.smirnov.guitarstoreproject.builder.Users.aUser;
import static by.smirnov.guitarstoreproject.constants.TestConstants.TEST_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private static final String LOGIN = "login";
    private static final User testUser = aUser().build();
    @Captor
    ArgumentCaptor<User> userCaptor;
    @Captor
    ArgumentCaptor<Long> idCaptor;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository repository;

    @Test
    @DisplayName("findById should return User")
    void checkFindByIdShouldReturnUser() {
        when(repository.findById(TEST_ID)).thenReturn(Optional.of(testUser));

        final User actual = userService.findById(TEST_ID);

        assertThat(actual).isEqualTo(testUser);
    }

    @Test
    @DisplayName("findByLogin should return User")
    void checkFindByLoginShouldReturnUser() {
        final User user = aUser().login(LOGIN).build();
        when(repository.findByLogin(LOGIN)).thenReturn(Optional.of(user));

        final User actual = userService.findByLogin(LOGIN);

        assertThat(actual).isEqualTo(user);
    }

    @Test
    @DisplayName("findAll should return list of users")
    void checkFindAllShouldReturnUsersList() {
        final Pageable pageable = mock(Pageable.class);
        List<User> users = getTestUsersList();
        Page<User> page = new PageImpl<>(users);
        doReturn(page).when(repository).findByIsDeleted(pageable, false);

        List<User> actual = userService.findAll(pageable);

        assertThat(users).isEqualTo(actual);
    }

    @Test
    @DisplayName("update should return User")
    void checkUpdateShouldReturnUser() {
        when(repository.save(testUser)).thenReturn(testUser);

        final User actual = userService.update(testUser);

        assertThat(actual).isEqualTo(testUser);
    }

    @Test
    @DisplayName("delete should pass argument true as isDeleted")
    void checkDeleteShouldPassArgumentIsDeletedTrue() {
        doReturn(Optional.of(testUser)).when(repository).findById(testUser.getId());

        userService.delete(testUser.getId());

        verify(repository).save(userCaptor.capture());
        User value = userCaptor.getValue();
        assertTrue(value.getIsDeleted());
    }

    @Test
    @DisplayName("hardDelete should pass id argument")
    void checkHardDeleteShouldPassIdAsArgument() {
        userService.hardDelete(TEST_ID);

        verify(repository).deleteById(idCaptor.capture());
        Long value = idCaptor.getValue();
        assertThat(value).isEqualTo(TEST_ID);
    }

    @Test
    @DisplayName("showDeleted should return list of users")
    void checkShowDeletedUsersShouldReturnUsersList() {
        final Pageable pageable = mock(Pageable.class);
        List<User> users = getTestUsersList();
        Page<User> page = new PageImpl<>(users);
        doReturn(page).when(repository).findByIsDeleted(pageable, true);

        Page<User> actual = userService.showDeletedUsers(pageable);

        assertThat(users).isEqualTo(actual.getContent());
    }

    private List<User> getTestUsersList(){
        return List.of(testUser, testUser);
    }
}
