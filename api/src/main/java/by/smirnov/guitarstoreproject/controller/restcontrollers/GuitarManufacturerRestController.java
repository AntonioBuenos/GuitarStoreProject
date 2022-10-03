package by.smirnov.guitarstoreproject.controller.restcontrollers;

import by.smirnov.guitarstoreproject.controller.constants.GuitarManufacturerControllerConstants;
import by.smirnov.guitarstoreproject.dto.GenreDTO;
import by.smirnov.guitarstoreproject.dto.GuitarManufacturerDTO;
import by.smirnov.guitarstoreproject.model.GuitarManufacturer;
import by.smirnov.guitarstoreproject.service.GuitarManufacturerService;
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
import static by.smirnov.guitarstoreproject.controller.constants.GuitarManufacturerControllerConstants.MANUFACTURERS;
import static by.smirnov.guitarstoreproject.controller.constants.GuitarManufacturerControllerConstants.MAPPING_MANUFACTURERS;

@RestController
@RequiredArgsConstructor
@RequestMapping(MAPPING_REST + MAPPING_MANUFACTURERS)
public class GuitarManufacturerRestController {

    private final GuitarManufacturerService service;

    private final EntityDTOConverter entityDTOConverter;

    @GetMapping()
    public ResponseEntity<?> index() {
        List<GuitarManufacturerDTO> manufacturers =  service.findAll().stream()
                .map(o -> (GuitarManufacturerDTO) entityDTOConverter.convertToDTO(o, GuitarManufacturerDTO.class))
                .toList();
        return manufacturers != null &&  !manufacturers.isEmpty()
                ? new ResponseEntity<>(Collections.singletonMap(MANUFACTURERS, manufacturers), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(MAPPING_ID)
    public ResponseEntity<GuitarManufacturerDTO> show(@PathVariable(ID) long id) {
        GuitarManufacturerDTO guitarManufacturerDTO =
                (GuitarManufacturerDTO) entityDTOConverter.convertToDTO(service.findById(id), GuitarManufacturerDTO.class);
        return guitarManufacturerDTO != null
                ? new ResponseEntity<>(guitarManufacturerDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //insert validation
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody GuitarManufacturerDTO guitarManufacturerDTO) {
        service.create((GuitarManufacturer) entityDTOConverter
                .convertToEntity(guitarManufacturerDTO, GuitarManufacturer.class));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //insert validation
    @PatchMapping(MAPPING_ID)
    public ResponseEntity<?> update(@PathVariable(name = ID) int id,
                                    @RequestBody GuitarManufacturerDTO guitarManufacturerDTO) {
        GuitarManufacturer guitarManufacturer =
                (GuitarManufacturer) entityDTOConverter.convertToEntity(guitarManufacturerDTO, GuitarManufacturer.class);
        final boolean updated = Objects.nonNull(service.update(guitarManufacturer));
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(MAPPING_ID)
    public ResponseEntity<?> delete(@PathVariable(ID) long id) {
        GuitarManufacturer guitarManufacturer = service.findById(id);
        if (!guitarManufacturer.getIsDeleted()) {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
