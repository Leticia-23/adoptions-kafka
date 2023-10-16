package com.hiberus.adoptionskafka.services;

import com.hiberus.adoptionskafka.dto.AnimalDto;
import com.hiberus.adoptionskafka.dto.InstitutionDto;
import com.hiberus.adoptionskafka.exceptions.AdoptionsMicroUnavailable;
import com.hiberus.adoptionskafka.exceptions.AnimalNotFoundException;
import com.hiberus.adoptionskafka.exceptions.InstitutionNotFoundException;
import org.springframework.http.ResponseEntity;

public interface AnimalFeignService {
    ResponseEntity<AnimalDto> checkAnimal(String idAnimal) throws AnimalNotFoundException, AdoptionsMicroUnavailable;

    ResponseEntity<InstitutionDto> checkInstitution(String idInstitution) throws InstitutionNotFoundException,  AdoptionsMicroUnavailable;
}
