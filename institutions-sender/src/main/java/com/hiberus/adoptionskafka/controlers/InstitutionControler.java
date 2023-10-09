package com.hiberus.adoptionskafka.controlers;

import com.hiberus.adoptionskafka.dto.InstitutionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


public interface InstitutionControler {

    @Operation(summary = "Create animal")
    @ApiResponse(responseCode = "202", description = "Request accepted")
    void createAnimal(InstitutionDto institutionDto);
}
