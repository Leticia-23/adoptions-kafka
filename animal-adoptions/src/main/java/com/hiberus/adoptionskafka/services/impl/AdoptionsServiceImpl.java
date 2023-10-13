package com.hiberus.adoptionskafka.services.impl;

import com.hiberus.adoptionskafka.dto.InstitutionDto;
import com.hiberus.adoptionskafka.models.Institution;
import com.hiberus.adoptionskafka.services.AdoptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import repositories.InstitutionsRepository;

import java.util.List;

public class AdoptionsServiceImpl implements AdoptionsService {
    @Autowired
    InstitutionsRepository institutionsRepository;

    @Override
    public void saveInstitution(Institution institution) {
        // POST or PUT
        institutionsRepository.save(institution);
    }

    @Override
    public List<Institution> findInstitutions(){
        return institutionsRepository.findAll();
    }
}
