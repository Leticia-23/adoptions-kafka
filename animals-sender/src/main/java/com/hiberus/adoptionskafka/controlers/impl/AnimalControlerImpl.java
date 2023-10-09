package com.hiberus.adoptionskafka.controlers.impl;

import com.hiberus.adoptionskafka.controlers.AnimalControler;
import com.hiberus.adoptionskafka.dto.AnimalDto;
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

}
