package com.hiberus.adoptionskafka.mappers;

import com.hiberus.adoptionskafka.avro.AnimalValue;
import com.hiberus.adoptionskafka.dto.AnimalDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AnimalMapper {

    @Mapping(target = "id", source = "uuid", qualifiedByName = "mapUuidToId")
    AnimalValue dtoToEntity(AnimalDto dto, String uuid);

    @Named("mapUuidToId")
    default String mapUuidToId(String uuid) {
        return uuid;
    }
}
