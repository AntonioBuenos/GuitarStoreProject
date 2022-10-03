package by.smirnov.guitarstoreproject.controller.restcontrollers;

import by.smirnov.guitarstoreproject.dto.InstockDTO;
import by.smirnov.guitarstoreproject.model.Instock;
import by.smirnov.guitarstoreproject.service.InstockService;
import by.smirnov.guitarstoreproject.util.EntityDTOConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static by.smirnov.guitarstoreproject.controller.constants.ControllerConstants.*;
import static by.smirnov.guitarstoreproject.controller.constants.InstockControllerConstants.INSTOCKS;
import static by.smirnov.guitarstoreproject.controller.constants.InstockControllerConstants.MAPPING_INSTOCKS;

@RestController
@RequiredArgsConstructor
@RequestMapping(MAPPING_REST + MAPPING_INSTOCKS)
public class InstockRestController {

    private final InstockService service;

    private final EntityDTOConverter entityDTOConverter;

    @GetMapping()
    public ResponseEntity<?> index() {
        List<InstockDTO> instokes = service.findAll().stream()
                .map(o -> (InstockDTO) entityDTOConverter.convertToDTO(o, InstockDTO.class))
                .toList();
        return instokes != null &&  !instokes.isEmpty()
                ? new ResponseEntity<>(Collections.singletonMap(INSTOCKS, instokes), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(MAPPING_ID)
    public ResponseEntity<InstockDTO> show(@PathVariable(ID) long id) {
        InstockDTO instokeDTO = (InstockDTO) entityDTOConverter.convertToDTO(service.findById(id), InstockDTO.class);
        return instokeDTO != null
                ? new ResponseEntity<>(instokeDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //insert validation
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody InstockDTO instokeDTO) {
        service.create((Instock) entityDTOConverter.convertToEntity(instokeDTO, Instock.class));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //insert validation
    @PatchMapping(MAPPING_ID)
    public ResponseEntity<?> update(@PathVariable(name = ID) int id, @RequestBody InstockDTO instokeDTO) {
        Instock instoke = (Instock) entityDTOConverter.convertToEntity(instokeDTO, Instock.class);
        final boolean updated = Objects.nonNull(service.update(instoke));
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

/*    @DeleteMapping(MAPPING_ID)
    public ResponseEntity<?> delete(@PathVariable(ID) long id) {
        Instock instoke = service.findById(id);
        if (!instoke.getIsDeleted()) {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }*/
}
