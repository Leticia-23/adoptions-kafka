package com.hiberus.adoptionskafka.services;

import com.hiberus.adoptionskafka.dto.InstitutionDto;
import com.hiberus.adoptionskafka.exceptions.InstitutionAlreadyExistsException;
import com.hiberus.adoptionskafka.exceptions.InstitutionNotFoundException;
import com.hiberus.adoptionskafka.models.Institution;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;

public interface AdoptionsService {
    void saveInstitution(Institution institution) throws  InstitutionAlreadyExistsException;

    void updateInstitution(Institution institution) throws InstitutionNotFoundException;

    List<Institution> findInstitutions();

    void deleteInstitution(String idInstitution) throws InstitutionNotFoundException;
}