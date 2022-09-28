package by.smirnov.guitarshopproject.controller;

import by.smirnov.guitarshopproject.dto.GuitarDTO;
import by.smirnov.guitarshopproject.model.Guitar;
import by.smirnov.guitarshopproject.service.GuitarService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static by.smirnov.guitarshopproject.controller.ControllerConstants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(MAPPING_REST_GUITARS)
public class GuitarRestController {
    private final GuitarService service;

    private final ModelMapper modelMapper;

    @GetMapping()
    public ResponseEntity<?> index() {
        List<GuitarDTO> guitars =  service.findAll().stream().map(this::convertToDTO).toList();
        return guitars != null &&  !guitars.isEmpty()
                ? new ResponseEntity<>(Collections.singletonMap(GUITARS, guitars), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(MAPPING_ID)
    public ResponseEntity<GuitarDTO> show(@PathVariable(ID) long id) {
        GuitarDTO guitarDTO = convertToDTO(service.findById(id));
        return guitarDTO != null
                ? new ResponseEntity<>(guitarDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //insert validation
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody GuitarDTO guitarDTO) {
        service.create(convertToEntity(guitarDTO));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //insert validation
    @PatchMapping(MAPPING_ID)
    public ResponseEntity<?> update(@PathVariable(name = ID) int id, @RequestBody GuitarDTO guitarDTO) {
        Guitar guitar = convertToEntity(guitarDTO);
        final boolean updated = Objects.nonNull(service.update(guitar));
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(MAPPING_ID)
    public ResponseEntity<?> delete(@PathVariable(ID) long id) {
        Guitar guitar = service.findById(id);
        if (!guitar.isDeleted()) {
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

    private Guitar convertToEntity(GuitarDTO guitarDTO){
        return modelMapper.map(guitarDTO, Guitar.class);
    }

    private GuitarDTO convertToDTO(Guitar guitar){
        return modelMapper.map(guitar, GuitarDTO.class);
    }
}
