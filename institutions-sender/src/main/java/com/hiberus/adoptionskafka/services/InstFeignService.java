package com.hiberus.adoptionskafka.services;

import com.hiberus.adoptionskafka.Exceptions.AdoptionsMicroUnavailable;
import com.hiberus.adoptionskafka.Exceptions.InstitutionNotFoundException;
import com.hiberus.adoptionskafka.dto.InstitutionDto;
import org.springframework.http.ResponseEntity;

public interface InstFeignService {
    ResponseEntity<InstitutionDto> checkInstitution(String idInstitution) throws InstitutionNotFoundException,  AdoptionsMicroUnavailable;
}
