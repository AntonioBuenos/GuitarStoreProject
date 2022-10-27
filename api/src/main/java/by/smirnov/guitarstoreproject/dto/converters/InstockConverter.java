package by.smirnov.guitarstoreproject.dto.converters;

import by.smirnov.guitarstoreproject.domain.Instock;
import by.smirnov.guitarstoreproject.domain.enums.GoodStatus;
import by.smirnov.guitarstoreproject.domain.enums.Placement;
import by.smirnov.guitarstoreproject.dto.instock.InstockCreateRequest;
import by.smirnov.guitarstoreproject.dto.instock.InstockRequest;
import by.smirnov.guitarstoreproject.dto.instock.InstockResponse;
import by.smirnov.guitarstoreproject.service.GuitarService;
import by.smirnov.guitarstoreproject.service.InstockService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class InstockConverter {

    private final ModelMapper modelMapper;
    private final InstockService service;
    private final GuitarService guitarService;

    public Instock convert(InstockCreateRequest request){
        Instock created = new Instock();
        created.setGoodStatus(GoodStatus.AVAILABLE);
        created.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
        created.setPlacement(Placement.valueOf(request.getPlacement()));
        created.setGuitarPosition(guitarService.findById(request.getGuitarId()));
        return created;
    }

    public Instock convert(InstockRequest request, Long instockId){
        Instock old = service.findById(instockId);
        old.setModificationDate(Timestamp.valueOf(LocalDateTime.now()));
        old.setPlacement(Placement.valueOf(request.getPlacement()));
        return old;
    }

    public InstockResponse convert(Instock response){
        return modelMapper.map(response, InstockResponse.class);
    }
}
