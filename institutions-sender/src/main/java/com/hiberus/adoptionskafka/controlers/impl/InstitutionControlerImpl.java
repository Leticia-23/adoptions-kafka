package com.hiberus.adoptionskafka.controlers.impl;

import com.hiberus.adoptionskafka.Exceptions.AdoptionsMicroUnavailable;
import com.hiberus.adoptionskafka.Exceptions.InstitutionNotFoundException;
import com.hiberus.adoptionskafka.controlers.InstitutionControler;
import com.hiberus.adoptionskafka.dto.EventType;
import com.hiberus.adoptionskafka.dto.InstitutionDto;
import com.hiberus.adoptionskafka.services.InstFeignService;
import com.hiberus.adoptionskafka.services.InstitutionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.google.gson.Gson;

import javax.validation.Valid;

@Slf4j
@Tag(name = "Insitutions API")
@RestController
@RequestMapping(value = "/institutions")
public class InstitutionControlerImpl implements InstitutionControler {

    @Autowired
    InstitutionService institutionService;

    @Autowired
    InstFeignService instFeignService;

    private final Gson gson = new Gson();

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void createInstitution(@Valid @RequestBody InstitutionDto institutionDto) {
        log.info("Receive http petition for create institution");
        institutionDto.setEventType(EventType.POST);
        institutionService.createInstitution(institutionDto);
    }

    @Override
    @PutMapping(value = "/{idInstitution}")
    public ResponseEntity<String> modifyInstitution(@PathVariable String idInstitution, @RequestBody InstitutionDto institutionDto) {
        log.info("Receive http petition for update institution");
        try {
            ResponseEntity<InstitutionDto> responseEntity = instFeignService.checkInstitution(idInstitution);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                log.info("Ha sido succes");
                institutionDto.setEventType(EventType.PUT);
                institutionService.updateInstitution(idInstitution, institutionDto);
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            }
            log.info("Ha recibido otro status code");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (InstitutionNotFoundException e) {
            return new ResponseEntity<>(gson.toJson(e.getMessage()),HttpStatus.NOT_FOUND);
        } catch ( AdoptionsMicroUnavailable e) {
            return new ResponseEntity<>(gson.toJson(e.getMessage()), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @Override
    @DeleteMapping(value = "/{idInstitution}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteInstitution(@PathVariable String idInstitution) {
        log.info("Receive http petition for delete institution");
        institutionService.deleteInstitution(idInstitution);
    }
}
