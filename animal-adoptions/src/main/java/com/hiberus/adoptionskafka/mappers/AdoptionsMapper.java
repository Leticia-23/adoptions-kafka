package com.hiberus.adoptionskafka.mappers;

import com.hiberus.adoptionskafka.avro.InstitutionAnimalsValue;
import com.hiberus.adoptionskafka.dto.InstitutionDto;
import com.hiberus.adoptionskafka.models.Institution;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdoptionsMapper {
    Institution avroToModel(InstitutionAnimalsValue institutionAnimalsValue);

    InstitutionDto modelToDto(Institution institution);
}
