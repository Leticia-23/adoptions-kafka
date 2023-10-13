package com.hiberus.adoptionskafka.services;

import com.hiberus.adoptionskafka.dto.InstitutionDto;
import com.hiberus.adoptionskafka.models.Institution;

public interface AdoptionsService {
    void saveInstitution(Institution institution);
}
