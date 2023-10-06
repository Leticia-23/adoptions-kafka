package com.hiberus.adoptionskafka.mappers;

import com.hiberus.adoptionskafka.avro.AnimalValue;
import com.hiberus.adoptionskafka.dto.AnimalDto;
import jakarta.ws.rs.core.Context;
import org.mapstruct.Mapping;

public interface AnimalMapper {
    @Mapping(target = "id", source = "uuid", qualifiedByName = "mapUuidToId")
    AnimalValue dtoToEntity(AnimalDto dto, @Context String uuid);

    default String mapUuidToId(String uuid) {
        // Puedes agregar lógica adicional si es necesario
        return uuid;
    }
}
