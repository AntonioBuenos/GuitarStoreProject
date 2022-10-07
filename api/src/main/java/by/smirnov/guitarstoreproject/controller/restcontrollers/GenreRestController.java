package by.smirnov.guitarstoreproject.controller.restcontrollers;

import by.smirnov.guitarstoreproject.dto.GenreDTO;
import by.smirnov.guitarstoreproject.service.GenreService;
import by.smirnov.guitarstoreproject.util.EntityDTOConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

import static by.smirnov.guitarstoreproject.controller.constants.ControllerConstants.*;
import static by.smirnov.guitarstoreproject.controller.constants.GenreControllerConstants.GENRES;
import static by.smirnov.guitarstoreproject.controller.constants.GenreControllerConstants.MAPPING_GENRES;

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
                ? new ResponseEntity<>(Collections.singletonMap(GENRES, genres), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(MAPPING_ID)
    public ResponseEntity<GenreDTO> show(@PathVariable(ID) long id) {
        GenreDTO genreDTO = (GenreDTO) entityDTOConverter.convertToDTO(service.findById(id), GenreDTO.class);
        return genreDTO != null
                ? new ResponseEntity<>(genreDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
