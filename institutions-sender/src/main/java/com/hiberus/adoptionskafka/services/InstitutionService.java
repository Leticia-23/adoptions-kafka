package com.hiberus.adoptionskafka.services;

import com.hiberus.adoptionskafka.Exceptions.AdoptionsMicroUnavailable;
import com.hiberus.adoptionskafka.Exceptions.InstitutionNotFoundException;
import com.hiberus.adoptionskafka.dto.InstitutionDto;
import org.springframework.http.ResponseEntity;

public interface InstitutionService {
    void createInstitution(InstitutionDto institution);

    void updateInstitution(String idInstitution, InstitutionDto institutionDto);

    void deleteInstitution(String idInstitution);
}
