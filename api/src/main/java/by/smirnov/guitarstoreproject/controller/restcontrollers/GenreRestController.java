package by.smirnov.guitarstoreproject.controller.restcontrollers;

import by.smirnov.guitarstoreproject.controller.constants.GenreControllerConstants;
import by.smirnov.guitarstoreproject.dto.GenreDTO;
import by.smirnov.guitarstoreproject.model.Genre;
import by.smirnov.guitarstoreproject.service.GenreService;
import by.smirnov.guitarstoreproject.util.EntityDTOConverter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static by.smirnov.guitarstoreproject.controller.constants.ControllerConstants.*;
import static by.smirnov.guitarstoreproject.controller.constants.GenreControllerConstants.MAPPING_GENRES;
import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
@RequestMapping(MAPPING_REST + MAPPING_GENRES)
public class GenreRestController {

    private final GenreService service;

    private final EntityDTOConverter entityDTOConverter;

    @GetMapping()
    public ResponseEntity<?> index() {
        List<GenreDTO> genres = service.findAll().stream()
                .map(o -> (GenreDTO) entityDTOConverter.convertToDTO(o, GenreDTO.class))
                .toList();
        return genres != null &&  !genres.isEmpty()
                ? new ResponseEntity<>(Collections.singletonMap(GenreControllerConstants.GENRES, genres), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(MAPPING_ID)
    public ResponseEntity<GenreDTO> show(@PathVariable(ID) long id) {
        GenreDTO genreDTO = (GenreDTO) entityDTOConverter.convertToDTO(service.findById(id), GenreDTO.class);
        return genreDTO != null
                ? new ResponseEntity<>(genreDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //insert validation
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody GenreDTO genreDTO) {
        service.create((Genre) entityDTOConverter.convertToEntity(genreDTO, Genre.class));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //insert validation
    @PatchMapping(MAPPING_ID)
    public ResponseEntity<?> update(@PathVariable(name = ID) int id, @RequestBody GenreDTO genreDTO) {
        Genre genre = (Genre) entityDTOConverter.convertToEntity(genreDTO, Genre.class);
        final boolean updated = Objects.nonNull(service.update(genre));
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(MAPPING_ID)
    public ResponseEntity<?> delete(@PathVariable(ID) long id) {
        Genre genre = service.findById(id);
        if (!genre.getIsDeleted()) {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
