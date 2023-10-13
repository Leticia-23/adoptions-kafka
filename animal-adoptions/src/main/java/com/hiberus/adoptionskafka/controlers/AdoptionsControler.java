package com.hiberus.adoptionskafka.controlers;

import com.hiberus.adoptionskafka.dto.AnimalDto;
import com.hiberus.adoptionskafka.dto.InstitutionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdoptionsControler {

    @Operation(summary = "Get institutions")
    @ApiResponse(responseCode = "200", description = "Users succesfully obtained")
    ResponseEntity<List<InstitutionDto>> getInstitutions();
}
