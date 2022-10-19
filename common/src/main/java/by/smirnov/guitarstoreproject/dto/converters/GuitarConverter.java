package by.smirnov.guitarstoreproject.dto.converters;

import by.smirnov.guitarstoreproject.dto.genre.GenreIdResponse;
import by.smirnov.guitarstoreproject.dto.guitar.GuitarRequest;
import by.smirnov.guitarstoreproject.dto.guitar.GuitarResponse;
import by.smirnov.guitarstoreproject.dto.user.UserChangeRequest;
import by.smirnov.guitarstoreproject.dto.user.UserCreateRequest;
import by.smirnov.guitarstoreproject.dto.user.UserResponse;
import by.smirnov.guitarstoreproject.model.Genre;
import by.smirnov.guitarstoreproject.model.Guitar;
import by.smirnov.guitarstoreproject.model.User;
import by.smirnov.guitarstoreproject.model.enums.Role;
import by.smirnov.guitarstoreproject.service.GenreService;
import by.smirnov.guitarstoreproject.service.GuitarService;
import by.smirnov.guitarstoreproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class GuitarConverter {

    private final ModelMapper modelMapper;
    private final GuitarService service;
    private final GenreService genreService;

    public Guitar convert(GuitarRequest request) {
        Guitar created = modelMapper.map(request, Guitar.class);
        created.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
        created.setIsDeleted(false);
        created.setGuitarGenres(getGenres(request));
        return created;
    }

    public Guitar convert(GuitarRequest request, Long id) {
        Guitar old = service.findById(id);
        Guitar changed = modelMapper.map(request, Guitar.class);
        changed.setModificationDate(Timestamp.valueOf(LocalDateTime.now()));
        changed.setCreationDate(old.getCreationDate());
        changed.setInstockGuitars(old.getInstockGuitars());
        changed.setIsDeleted(old.getIsDeleted());
        changed.setGuitarGenres(getGenres(request));
        return changed;
    }

    public GuitarResponse convert(Guitar guitar) {
        return modelMapper.map(guitar, GuitarResponse.class);
    }

    private Set<Genre> getGenres(GuitarRequest request) {
        Set<Genre> genres = new HashSet<>();
        for (GenreIdResponse genre : request.getGuitarGenres()) {
            Genre genreDB = genreService.findById(genre.getId());
            genres.add(genreDB);
        }
        return genres;
    }
}
