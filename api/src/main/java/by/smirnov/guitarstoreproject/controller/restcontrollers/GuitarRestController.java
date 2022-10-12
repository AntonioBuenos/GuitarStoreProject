package by.smirnov.guitarstoreproject.controller.restcontrollers;

import by.smirnov.guitarstoreproject.dto.GuitarDTO;
import by.smirnov.guitarstoreproject.model.Guitar;
import by.smirnov.guitarstoreproject.service.GuitarService;
import by.smirnov.guitarstoreproject.util.EntityDTOConverter;
import by.smirnov.guitarstoreproject.util.ValidationErrorConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static by.smirnov.guitarstoreproject.controller.constants.ControllerConstants.*;
import static by.smirnov.guitarstoreproject.controller.constants.GuitarControllerConstants.GUITARS;
import static by.smirnov.guitarstoreproject.controller.constants.GuitarControllerConstants.MAPPING_GUITARS;

@RestController
@RequiredArgsConstructor
@RequestMapping(MAPPING_REST + MAPPING_GUITARS)
public class GuitarRestController {
    private final GuitarService service;

    private final EntityDTOConverter entityDTOConverter;

    @GetMapping()
    public ResponseEntity<?> index() {
        List<GuitarDTO> guitars =  service.findAll().stream()
                .map(o -> (GuitarDTO) entityDTOConverter.convertToDTO(o, GuitarDTO.class))
                .toList();
        return guitars != null &&  !guitars.isEmpty()
                ? new ResponseEntity<>(Collections.singletonMap(GUITARS, guitars), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(MAPPING_ID)
    public ResponseEntity<GuitarDTO> show(@PathVariable(ID) long id) {
        GuitarDTO guitarDTO = (GuitarDTO) entityDTOConverter.convertToDTO(service.findById(id), GuitarDTO.class);
        return guitarDTO != null
                ? new ResponseEntity<>(guitarDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody @Valid GuitarDTO guitarDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ValidationErrorConverter.getErrors(bindingResult);
            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        }

        service.create((Guitar) entityDTOConverter.convertToEntity(guitarDTO, Guitar.class));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping(MAPPING_ID)
    public ResponseEntity<?> update(@PathVariable(name = ID) int id,
                                    @RequestBody @Valid GuitarDTO guitarDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ValidationErrorConverter.getErrors(bindingResult);
            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        }

        Guitar guitar = (Guitar) entityDTOConverter.convertToEntity(guitarDTO, Guitar.class);
        final boolean updated = Objects.nonNull(service.update(guitar));
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(MAPPING_ID)
    public ResponseEntity<?> delete(@PathVariable(ID) long id) {
        Guitar guitar = service.findById(id);
        if (!guitar.getIsDeleted()) {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping(MAPPING_STATS)
    public ResponseEntity<?> getAveragePrice() {
        return new ResponseEntity<>
                (Collections.singletonMap(AVG, service.showAverageGuitarPrice()), HttpStatus.OK);
    }

    @DeleteMapping(MAPPING_ID + MAPPING_HARD_DELETE)
    public ResponseEntity<?> hardDelete(@PathVariable(ID) long id) {
        service.hardDelete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
