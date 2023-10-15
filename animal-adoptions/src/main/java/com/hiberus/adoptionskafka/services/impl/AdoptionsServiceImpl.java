package com.hiberus.adoptionskafka.services.impl;

import com.hiberus.adoptionskafka.exceptions.InstitutionAlreadyExistsException;
import com.hiberus.adoptionskafka.exceptions.InstitutionNotFoundException;
import com.hiberus.adoptionskafka.models.Animal;
import com.hiberus.adoptionskafka.models.Institution;
import com.hiberus.adoptionskafka.services.AdoptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hiberus.adoptionskafka.repositories.InstitutionsRepository;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;
import java.util.Optional;

@Service
public class AdoptionsServiceImpl implements AdoptionsService {
    @Autowired
    InstitutionsRepository institutionsRepository;

    @Override
    public void saveInstitution(Institution institution) throws InstitutionAlreadyExistsException {
        if (!institutionsRepository.existsById(institution.getIdInstitution())) {
            institutionsRepository.save(institution);
        } else {
            throw new InstitutionAlreadyExistsException();
        }

    }

    @Override
    public void updateInstitution(Institution institution) throws InstitutionNotFoundException {
        if (institutionsRepository.existsById(institution.getIdInstitution())) {
            institutionsRepository.save(institution);
        } else {
            throw new InstitutionNotFoundException();
        }
    }

    @Override
    public List<Institution> findInstitutions(){
        return institutionsRepository.findAll();
    }

    @Override
    public void deleteInstitution(String idInstitution) throws InstitutionNotFoundException {

        if (institutionsRepository.existsById(idInstitution)) {
            institutionsRepository.deleteById(idInstitution);
        } else {
            throw new InstitutionNotFoundException();
        }
    }

    @Override
    public void deleteAnimalsFromInstitution(Institution institution) throws InstitutionNotFoundException {

        Optional<Institution> optionalInstitutionSaved = institutionsRepository.findById(institution.getIdInstitution());

        Institution institutionSaved = optionalInstitutionSaved.orElseThrow(InstitutionNotFoundException::new);

        List<Animal> animalsToRemove = institution.getAnimals();

        // Delete only the new animal to delete because the others already deleted will be skipped
        institutionSaved.getAnimals().removeAll(animalsToRemove);

        // Set to the updated institution the animal list without the animals to delete
        institution.setAnimals(institutionSaved.getAnimals());

        // Update institution
        institutionsRepository.save(institution);

    }
}
