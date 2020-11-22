package br.com.utily.ecommerce.helper.view;

import br.com.utily.ecommerce.dto.DTOEntity;
import br.com.utily.ecommerce.entity.Entity;
import org.modelmapper.ModelMapper;

import java.lang.reflect.Type;

public class ModelMapperHelper {

    private static final ModelMapper modelMapper = new ModelMapper();

    private ModelMapperHelper() { }

    public static <T extends Entity, R extends DTOEntity> R fromEntityToDTO(T entity, Type type) {
        return modelMapper.map(entity, type);
    }

    public static <T extends DTOEntity, R extends Entity> R fromDTOToEntity(T dto, Type type) {
        return modelMapper.map(dto, type);
    }
}
