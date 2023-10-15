package com.hiberus.adoptionskafka.controlers.impl;

import com.hiberus.adoptionskafka.controlers.AnimalControler;
import com.hiberus.adoptionskafka.dto.AnimalDto;
import com.hiberus.adoptionskafka.dto.EventType;
import com.hiberus.adoptionskafka.services.AnimalService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Tag(name = "Animals API")
@RestController
@RequestMapping(value = "/animals")
public class AnimalControlerImpl implements AnimalControler {

    @Autowired
    AnimalService animalService;

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void createAnimal(@Valid @RequestBody AnimalDto animalDto) {
        log.info("Receive http petition for create animal");
        animalService.createAnimal(animalDto);
    }

    @Override
    @PutMapping(value = "/{idInstitution}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void modifyAnimal(@PathVariable String idAnimal, @RequestBody AnimalDto animalDto) {
        log.info("Receive http petition for update animal");
        animalDto.setEventType(EventType.PUT);
        animalService.updateAnimal(idAnimal, animalDto);
    }

    @Override
    @DeleteMapping(value = "/{idInstitution}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteAnimal(@PathVariable String idAnimal, @RequestBody AnimalDto animalDto) {
        log.info("Receive http petition for delete animal");
        animalDto.setEventType(EventType.DELETE);
        animalService.deleteAnimal(idAnimal, animalDto);
    }
}
