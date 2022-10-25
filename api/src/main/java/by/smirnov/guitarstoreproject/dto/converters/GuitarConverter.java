package by.smirnov.guitarstoreproject.dto.converters;

import by.smirnov.guitarstoreproject.dto.genre.GenreIdRequest;
import by.smirnov.guitarstoreproject.dto.guitar.GuitarRequest;
import by.smirnov.guitarstoreproject.dto.guitar.GuitarResponse;
import by.smirnov.guitarstoreproject.model.Genre;
import by.smirnov.guitarstoreproject.model.Guitar;
import by.smirnov.guitarstoreproject.service.GenreService;
import by.smirnov.guitarstoreproject.service.GuitarManufacturerService;
import by.smirnov.guitarstoreproject.service.GuitarService;
import com.neovisionaries.i18n.CountryCode;
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
    private final GuitarManufacturerService guitarManufacturerService;

    public Guitar convert(GuitarRequest request) {
        Guitar created = modelMapper.map(request, Guitar.class);
        created.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
        created.setIsDeleted(false);
        created.setGuitarGenres(getGenres(request));
        created.setManufacturer(guitarManufacturerService.findById(request.getManufacturer().getId()));
        return created;
    }

    public Guitar convert(GuitarRequest request, Long id) {
        Guitar old = service.findById(id);
        old.setModificationDate(Timestamp.valueOf(LocalDateTime.now()));
        old.setTypeof(request.getTypeof());
        old.setShape(request.getShape());
        old.setSeries(request.getSeries());
        old.setModel(request.getModel());
        old.setStringsQnt(request.getStringsQnt());
        old.setNeck(request.getNeck());
        old.setBridge(request.getBridge());
        old.setBodyMaterial(request.getBodyMaterial());
        old.setPrice(request.getPrice());
        old.setProdCountry(CountryCode.valueOf(request.getProdCountry()));
        old.setManufacturer(guitarManufacturerService.findById(request.getManufacturer().getId()));
        old.setGuitarGenres(getGenres(request));
        return old;
    }

    public GuitarResponse convert(Guitar guitar) {
        return modelMapper.map(guitar, GuitarResponse.class);
    }

    private Set<Genre> getGenres(GuitarRequest request) {
        Set<Genre> genres = new HashSet<>();
        for (GenreIdRequest genre : request.getGuitarGenres()) {
            Genre genreDB = genreService.findById(genre.getId());
            genres.add(genreDB);
        }
        return genres;
    }
}
