package com.hiberus.adoptionskafka.mappers;

import com.hiberus.adoptionskafka.avro.InstitutionValue;
import com.hiberus.adoptionskafka.dto.InstitutionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface InstitutionMapper {

    @Mapping(target = "id", source = "uuid", qualifiedByName = "mapUuidToId")
    InstitutionValue dtoToEntity(InstitutionDto dto, String uuid);

    @Named("mapUuidToId")
    default String mapUuidToId(String uuid) {
        return uuid;
    }
}
