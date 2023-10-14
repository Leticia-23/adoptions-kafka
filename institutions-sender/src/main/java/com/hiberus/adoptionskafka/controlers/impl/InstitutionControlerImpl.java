package com.hiberus.adoptionskafka.controlers.impl;

import com.hiberus.adoptionskafka.controlers.InstitutionControler;
import com.hiberus.adoptionskafka.dto.EventType;
import com.hiberus.adoptionskafka.dto.InstitutionDto;
import com.hiberus.adoptionskafka.services.InstitutionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Tag(name = "Insitutions API")
@RestController
@RequestMapping(value = "/institutions")
public class InstitutionControlerImpl implements InstitutionControler {

    @Autowired
    InstitutionService institutionService;

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
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void modifyInstitution(@PathVariable String idInstitution, @RequestBody InstitutionDto institutionDto) {
        log.info("Receive http petition for update institution");
        institutionDto.setEventType(EventType.PUT);
        institutionService.updateInstitution(idInstitution, institutionDto);
    }

    @Override
    @DeleteMapping(value = "/{idInstitution}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteInstitution(@PathVariable String idInstitution) {
        log.info("Receive http petition for delete institution");
        institutionService.deleteInstitution(idInstitution);
    }
}
