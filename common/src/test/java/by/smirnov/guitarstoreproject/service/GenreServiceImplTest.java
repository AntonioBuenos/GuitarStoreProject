package by.smirnov.guitarstoreproject.service;

import by.smirnov.guitarstoreproject.domain.Genre;
import by.smirnov.guitarstoreproject.repository.GenreRepository;
import org.junit.jupiter.api.BeforeEach;
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

import static by.smirnov.guitarstoreproject.builder.Genres.aGenre;
import static by.smirnov.guitarstoreproject.constants.TestConstants.TEST_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GenreServiceImplTest {

    @Mock
    private GenreRepository repository;

    @InjectMocks
    private GenreServiceImpl service;

    @Captor
    ArgumentCaptor<Genre> genreCaptor;

    @Captor
    ArgumentCaptor<Long> idCaptor;

    private Genre testGenre;

    @BeforeEach
    void init(){
        testGenre = aGenre().build();
    }

    @Test
    @DisplayName("findById should return Genre")
    void checkFindByIdShouldReturnGenre() {
        doReturn(Optional.of(testGenre)).when(repository).findById(TEST_ID);

        Genre actual = service.findById(TEST_ID);

        assertThat(actual).isEqualTo(testGenre);
    }

    @Test
    @DisplayName("findAll should return list of genres")
    void checkFindAllShouldReturnGenreList() {
        Pageable pageable = mock(Pageable.class);
        List<Genre> genres = List.of(testGenre, testGenre);
        Page<Genre> page = new PageImpl<>(genres);
        doReturn(page).when(repository).findAll(pageable);

        List<Genre> actual = service.findAll(pageable);

        assertThat(genres).isEqualTo(actual);
    }

    @Test
    @DisplayName("create should return Genre")
    void checkCreateShouldReturnGenre() {
        doReturn(testGenre).when(repository).save(testGenre);

        Genre actual = service.create(testGenre);

        assertThat(actual).isEqualTo(testGenre);
    }

    @Test
    @DisplayName("update should return Genre")
    void checkUpdateShouldReturnGenre() {
        doReturn(testGenre).when(repository).save(testGenre);

        Genre actual = service.update(testGenre);

        assertThat(actual).isEqualTo(testGenre);
    }

    @Test
    @DisplayName("delete should pass argument true as isDeleted")
    void checkDeleteShouldPassArgumentIsDeletedTrue() {
        doReturn(Optional.of(testGenre)).when(repository).findById(testGenre.getId());
        service.delete(testGenre.getId());

        verify(repository).save(genreCaptor.capture());
        Genre value = genreCaptor.getValue();
        assertTrue(value.getIsDeleted());
    }

    @Test
    @DisplayName("hardDelete should pass id argument")
    void checkHardDeleteShouldCallRepository() {
        service.hardDelete(TEST_ID);

        verify(repository).deleteById(idCaptor.capture());
        Long value = idCaptor.getValue();
        assertThat(value).isEqualTo(TEST_ID);
    }
}
