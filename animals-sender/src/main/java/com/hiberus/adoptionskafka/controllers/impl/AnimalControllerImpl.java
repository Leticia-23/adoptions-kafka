package com.hiberus.adoptionskafka.controllers.impl;

import com.hiberus.adoptionskafka.controllers.AnimalController;
import com.hiberus.adoptionskafka.dto.AnimalDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/animals")
public class AnimalControllerImpl implements AnimalController {

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void createAnimal(@Valid @RequestBody AnimalDto animalDto) {
        log.debug("Receive http petition for create animal");

    }


}
