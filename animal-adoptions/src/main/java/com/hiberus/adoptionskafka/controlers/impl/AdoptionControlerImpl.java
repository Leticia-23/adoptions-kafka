package com.hiberus.adoptionskafka.controlers.impl;

import com.hiberus.adoptionskafka.controlers.AdoptionsControler;
import com.hiberus.adoptionskafka.dto.AnimalDto;
import com.hiberus.adoptionskafka.dto.InstitutionDto;
import com.hiberus.adoptionskafka.exceptions.AnimalNotFoundException;
import com.hiberus.adoptionskafka.exceptions.InstitutionNotFoundException;
import com.hiberus.adoptionskafka.mappers.AdoptionsMapper;
import com.hiberus.adoptionskafka.services.AdoptionsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Adoptions API")
@RestController
@RequestMapping(value = "/adoptions")
public class AdoptionControlerImpl implements AdoptionsControler {

    @Autowired
    AdoptionsService adoptionsService;

    @Autowired
    AdoptionsMapper adoptionsMapper;

    @Override
    @GetMapping
    public ResponseEntity<List<InstitutionDto>> getInstitutions(){
        List<InstitutionDto> list = adoptionsService.findInstitutions()
                .stream()
                .map(institution -> adoptionsMapper.modelToDto(institution))
                .toList();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/institutions/{idInstitution}")
    public ResponseEntity<InstitutionDto> getInstitutionById(@PathVariable String idInstitution) {
        try {
            InstitutionDto institution = adoptionsMapper.modelToDto(adoptionsService.findInstitution(idInstitution));
            return new ResponseEntity<>(institution, HttpStatus.OK);
        }
        catch (InstitutionNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @GetMapping(value = "/animals/{idAnimal}")
    public ResponseEntity<AnimalDto> getAnimalById(@PathVariable String idAnimal) {
        try {
            AnimalDto animal = adoptionsMapper.animalModelToDTo(adoptionsService.findAnimal(idAnimal));
            return new ResponseEntity<>(animal, HttpStatus.OK);
        }
        catch (AnimalNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
