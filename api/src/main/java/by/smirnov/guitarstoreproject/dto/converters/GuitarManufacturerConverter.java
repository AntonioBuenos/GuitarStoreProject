package by.smirnov.guitarstoreproject.dto.converters;

import by.smirnov.guitarstoreproject.domain.GuitarManufacturer;
import by.smirnov.guitarstoreproject.dto.manufacturer.GuitarManufacturerRequest;
import by.smirnov.guitarstoreproject.dto.manufacturer.GuitarManufacturerResponse;
import by.smirnov.guitarstoreproject.service.GuitarManufacturerService;
import by.smirnov.guitarstoreproject.validation.CountryFinder;
import com.neovisionaries.i18n.CountryCode;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class GuitarManufacturerConverter {

    private final ModelMapper modelMapper;
    private final GuitarManufacturerService service;
    private final CountryFinder countryFinder;

    public GuitarManufacturer convert(GuitarManufacturerRequest request){
        GuitarManufacturer created = modelMapper.map(request, GuitarManufacturer.class);
        created.setOriginCountry(countryFinder.getCountryCode(request.getOriginCountry()));
        created.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
        created.setIsDeleted(false);
        return created;
    }

    public GuitarManufacturer convert(GuitarManufacturerRequest request, Long id){
        GuitarManufacturer old = service.findById(id);
        old.setBrand(request.getBrand());
        old.setCompany(request.getCompany());
        old.setOriginCountry(countryFinder.getCountryCode(request.getOriginCountry()));
        old.setModificationDate(Timestamp.valueOf(LocalDateTime.now()));
        return old;
    }

    public GuitarManufacturerResponse convert(GuitarManufacturer guitar){
        return modelMapper.map(guitar, GuitarManufacturerResponse.class);
    }
}
