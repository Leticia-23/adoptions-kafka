package com.hiberus.adoptionskafka.services;

import com.hiberus.adoptionskafka.dto.InstitutionDto;

public interface InstitutionService {
    void createInstitution(InstitutionDto institution);
}
