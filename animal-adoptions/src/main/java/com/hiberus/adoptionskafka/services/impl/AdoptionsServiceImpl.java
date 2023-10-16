package com.hiberus.adoptionskafka.services.impl;

import com.hiberus.adoptionskafka.exceptions.AnimalNotFoundException;
import com.hiberus.adoptionskafka.exceptions.InstitutionAlreadyExistsException;
import com.hiberus.adoptionskafka.exceptions.InstitutionNotFoundException;
import com.hiberus.adoptionskafka.models.Animal;
import com.hiberus.adoptionskafka.models.Institution;
import com.hiberus.adoptionskafka.repositories.AnimalsRepository;
import com.hiberus.adoptionskafka.services.AdoptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hiberus.adoptionskafka.repositories.InstitutionsRepository;

import java.util.List;


@Service
public class AdoptionsServiceImpl implements AdoptionsService {
    @Autowired
    InstitutionsRepository institutionsRepository;

    @Autowired
    AnimalsRepository animalsRepository;

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
            // This update the institution -> delete the relations institution-animals
            // -> create the new relations institution-animal
            // -> delete animals from the Animals table that there are not in the relation
            institutionsRepository.save(institution);
        } else {
            throw new InstitutionNotFoundException();
        }
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
    public List<Institution> findInstitutions(){
        return institutionsRepository.findAll();
    }

    @Override
    public Institution findInstitution(String idInstitution) throws InstitutionNotFoundException {
        return institutionsRepository.findById(idInstitution).orElseThrow(InstitutionNotFoundException::new);
    }

    @Override
    public Animal findAnimal(String idAnimal) throws AnimalNotFoundException {
        return animalsRepository.findById(idAnimal).orElseThrow(AnimalNotFoundException::new);
    }
}
