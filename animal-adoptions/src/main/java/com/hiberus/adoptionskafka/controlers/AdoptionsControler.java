package com.hiberus.adoptionskafka.controlers;

import com.hiberus.adoptionskafka.dto.AnimalDto;
import com.hiberus.adoptionskafka.dto.InstitutionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface AdoptionsControler {

    @Operation(summary = "Get institutions")
    @ApiResponse(responseCode = "200", description = "Institutions successfully obtained")
    ResponseEntity<List<InstitutionDto>> getInstitutions();

    @Operation(summary = "Get concrete institution by IdInstitution")
    @ApiResponse(responseCode = "200", description = "Institution successfully obtained")
    @ApiResponse(responseCode = "404", description = "Institution not found")
    ResponseEntity<InstitutionDto> getInstitutionById(@PathVariable String idInstitution);
}
