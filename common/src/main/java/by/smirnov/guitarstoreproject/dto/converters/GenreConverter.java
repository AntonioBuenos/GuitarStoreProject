package by.smirnov.guitarstoreproject.dto.converters;

import by.smirnov.guitarstoreproject.dto.genre.GenreRequest;
import by.smirnov.guitarstoreproject.dto.genre.GenreResponse;
import by.smirnov.guitarstoreproject.model.Genre;
import by.smirnov.guitarstoreproject.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class GenreConverter {

    private final ModelMapper modelMapper;
    private final GenreService service;

    public Genre convert(GenreRequest request){
        Genre created = modelMapper.map(request, Genre.class);
        created.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
        created.setIsDeleted(false);
        return created;
    }

    public Genre convert(GenreRequest request, Long id){
        Genre old = service.findById(id);
        old.setMusicGenre(request.getMusicGenre());
        old.setModificationDate(Timestamp.valueOf(LocalDateTime.now()));
        return old;
    }

    public GenreResponse convert(Genre genre){
        return modelMapper.map(genre, GenreResponse.class);
    }
}
