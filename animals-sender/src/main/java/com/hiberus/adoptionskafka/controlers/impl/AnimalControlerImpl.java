package com.hiberus.adoptionskafka.controlers.impl;

import com.google.gson.Gson;
import com.hiberus.adoptionskafka.controlers.AnimalControler;
import com.hiberus.adoptionskafka.dto.AnimalDto;
import com.hiberus.adoptionskafka.dto.EventType;
import com.hiberus.adoptionskafka.dto.InstitutionDto;
import com.hiberus.adoptionskafka.exceptions.AdoptionsMicroUnavailable;
import com.hiberus.adoptionskafka.exceptions.AnimalNotFoundException;
import com.hiberus.adoptionskafka.exceptions.InstitutionNotFoundException;
import com.hiberus.adoptionskafka.services.AnimalFeignService;
import com.hiberus.adoptionskafka.services.AnimalService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Tag(name = "Animals API")
@RestController
@RequestMapping(value = "/animals")
public class AnimalControlerImpl implements AnimalControler {

    @Autowired
    AnimalService animalService;

    @Autowired
    AnimalFeignService animalFeignService;

    private final Gson gson = new Gson();

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> createAnimal(@Valid @RequestBody AnimalDto animalDto) {
        log.info("Receive http petition for create animal");

        try {
            ResponseEntity<InstitutionDto> responseEntityInst = animalFeignService.checkInstitution(animalDto.getIdInstitution());
            if (responseEntityInst.getStatusCode().is2xxSuccessful()) {
                animalDto.setEventType(EventType.POST);
                animalService.createAnimal(animalDto);
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (InstitutionNotFoundException e) {
            return new ResponseEntity<>(gson.toJson(e.getMessage()),HttpStatus.NOT_FOUND);
        } catch (AdoptionsMicroUnavailable e) {
            return new ResponseEntity<>(gson.toJson(e.getMessage()), HttpStatus.SERVICE_UNAVAILABLE);
        }



    }

    @Override
    @PutMapping(value = "/{idAnimal}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> modifyAnimal(@PathVariable String idAnimal, @RequestBody AnimalDto animalDto) {
        log.info("Receive http petition for update animal");

        try {
            ResponseEntity<AnimalDto> responseEntityAnimal = animalFeignService.checkAnimal(idAnimal);

            ResponseEntity<InstitutionDto> responseEntityInst = animalFeignService.checkInstitution(animalDto.getIdInstitution());

            if (responseEntityAnimal.getStatusCode().is2xxSuccessful() &&
                    responseEntityInst.getStatusCode().is2xxSuccessful()) {
                animalDto.setEventType(EventType.PUT);
                animalService.updateAnimal(idAnimal, animalDto);
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (InstitutionNotFoundException | AnimalNotFoundException e) {
            return new ResponseEntity<>(gson.toJson(e.getMessage()),HttpStatus.NOT_FOUND);
        } catch ( AdoptionsMicroUnavailable e) {
            return new ResponseEntity<>(gson.toJson(e.getMessage()), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @Override
    @DeleteMapping(value = "/{idAnimal}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public  ResponseEntity<String> deleteAnimal(@PathVariable String idAnimal, @RequestBody AnimalDto animalDto) {
        log.info("Receive http petition for delete animal");

        try {
            ResponseEntity<AnimalDto> responseEntityAnimal = animalFeignService.checkAnimal(idAnimal);

            ResponseEntity<InstitutionDto> responseEntityInst = animalFeignService.checkInstitution(animalDto.getIdInstitution());

            if (responseEntityAnimal.getStatusCode().is2xxSuccessful() &&
                    responseEntityInst.getStatusCode().is2xxSuccessful()) {
                animalDto.setEventType(EventType.DELETE);
                animalService.deleteAnimal(idAnimal, animalDto);
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (InstitutionNotFoundException | AnimalNotFoundException e) {
            return new ResponseEntity<>(gson.toJson(e.getMessage()),HttpStatus.NOT_FOUND);
        } catch ( AdoptionsMicroUnavailable e) {
            return new ResponseEntity<>(gson.toJson(e.getMessage()), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
