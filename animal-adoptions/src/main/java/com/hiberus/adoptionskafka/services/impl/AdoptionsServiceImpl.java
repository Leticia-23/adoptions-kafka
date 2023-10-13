package com.hiberus.adoptionskafka.services.impl;

import com.hiberus.adoptionskafka.models.Institution;
import com.hiberus.adoptionskafka.services.AdoptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hiberus.adoptionskafka.repositories.InstitutionsRepository;

import java.util.List;

@Service
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
