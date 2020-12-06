package br.com.utily.ecommerce.helper.view;

import br.com.utily.ecommerce.dto.DTOEntity;
import br.com.utily.ecommerce.entity.Entity;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;

import java.lang.reflect.Type;

public class ModelMapperHelper {

    private static ModelMapper modelMapper = new ModelMapper();

    private ModelMapperHelper() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
    }

    public static <T extends Entity, R extends DTOEntity> R fromEntityToDTO(T entity, Type type) {
        R mapped = modelMapper.map(entity, type);
        reloadMapper();

        return mapped;
    }

    private static void reloadMapper() {
        modelMapper = new ModelMapper();
    }

    public static <T extends DTOEntity, R extends Entity> R fromDTOToEntity(T dto, Type type) {
        R mapped = modelMapper.map(dto, type);
        reloadMapper();

        return mapped;
    }

    public static <S extends DTOEntity, D extends Entity> TypeMap<S, D> configureTypeMapWithDTOSource(Class<S> sourceType, Class<D> destinationType) {
        return modelMapper.typeMap(sourceType, destinationType);
    }

    public static <S extends Entity, D extends DTOEntity> TypeMap<S, D> configureTypeMapWithEntitySource(Class<S> sourceType, Class<D> destinationType) {
        return modelMapper.typeMap(sourceType, destinationType);
    }

    public static <S extends DTOEntity, D extends Entity > void addEntityFieldsToSkip(PropertyMap<S, D> skipEntityFieldsMap) {
        modelMapper.addMappings(skipEntityFieldsMap);
    }

    public static <S extends Entity, D extends DTOEntity> void addDTOFieldsToSkip(PropertyMap<S, D> skipDTOFieldsMap) {
        modelMapper.addMappings(skipDTOFieldsMap);
    }
}
