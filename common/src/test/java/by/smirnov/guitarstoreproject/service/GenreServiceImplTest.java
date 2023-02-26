package by.smirnov.guitarstoreproject.service;

import by.smirnov.guitarstoreproject.domain.Genre;
import by.smirnov.guitarstoreproject.domain.enums.MusicGenre;
import by.smirnov.guitarstoreproject.repository.GenreRepository;
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
class GenreServiceImplTest {

    private static final long ID = 1L;

    @Mock
    private GenreRepository repository;

    @InjectMocks
    private GenreServiceImpl service;

    @Captor
    ArgumentCaptor<Genre> genreCaptor;

    final Genre testGenre = Genre.builder()
            .id(ID)
            .byGenreGuitars(new ArrayList<>())
            .musicGenre(MusicGenre.METAL)
            .isDeleted(false)
            .build();

    @Test
    @DisplayName("findById should return Genre")
    void checkFindByIdShouldReturnGenre() {
        final Genre genre = mock(Genre.class);
        when(repository.findById(ID)).thenReturn(Optional.ofNullable(genre));

        final Genre actual = service.findById(ID);

        assertThat(actual).isEqualTo(genre);
    }

    @Test
    @DisplayName("findAll should return list of genres")
    void checkFindAllShouldReturnGenreList() {
        final Pageable pageable = mock(Pageable.class);
        List<Genre> genres = new ArrayList<>();
        genres.add(mock(Genre.class));
        genres.add(mock(Genre.class));
        Page<Genre> page = new PageImpl<>(genres);
        doReturn(page).when(repository).findAll(pageable);

        List<Genre> actual = service.findAll(pageable);

        assertThat(genres).isEqualTo(actual);
    }

    @Test
    @DisplayName("create should return Genre")
    void checkCreateShouldReturnGenre() {
        final Genre genre = mock(Genre.class);
        when(repository.save(genre)).thenReturn(genre);

        final Genre actual = service.create(genre);

        assertThat(actual).isEqualTo(genre);
    }

    @Test
    @DisplayName("update should return Genre")
    void checkUpdateShouldReturnGenre() {
        final Genre genre = mock(Genre.class);
        when(repository.save(genre)).thenReturn(genre);

        final Genre actual = service.update(genre);

        assertThat(actual).isEqualTo(genre);
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
    @DisplayName("hardDelete should call repository")
    void checkHardDeleteShouldCallRepository() {
        service.hardDelete(ID);

        verify(repository).deleteById(ID);
    }
}
