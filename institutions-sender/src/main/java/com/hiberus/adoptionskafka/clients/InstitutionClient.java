package com.hiberus.adoptionskafka.clients;

import com.hiberus.adoptionskafka.dto.InstitutionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "animal-adoptions")
public interface InstitutionClient {
    @GetMapping(value = "/adoptions/institutions/{idInstitution}")
    ResponseEntity<InstitutionDto> getInstitutionById(@PathVariable String idInstitution);
}
