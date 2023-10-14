package com.hiberus.adoptionskafka.services;

import com.hiberus.adoptionskafka.dto.InstitutionDto;
import com.hiberus.adoptionskafka.models.Institution;

import java.util.List;

public interface AdoptionsService {
    void saveInstitution(Institution institution);

    List<Institution> findInstitutions();

    void deleteInstitution(String idInstitution);
}