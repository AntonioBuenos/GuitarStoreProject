package by.smirnov.guitarstoreproject.util;

import by.smirnov.guitarstoreproject.dto.ObjectDTO;
import by.smirnov.guitarstoreproject.model.ObjectEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EntityDTOConverter {

    private final ModelMapper modelMapper;

    public ObjectEntity convertToEntity(ObjectDTO objectDTO, Class<? extends ObjectEntity> outputClass){
        return modelMapper.map(objectDTO, outputClass);
    }

    public ObjectDTO convertToDTO(ObjectEntity objectEntity, Class<? extends ObjectDTO> outputClass){
        return modelMapper.map(objectEntity, outputClass);
    }
}
