package com.hiberus.adoptionskafka.controllers;

import com.hiberus.adoptionskafka.dto.AnimalDto;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


public interface AnimalController {

    @Operation(summary = "Create animal")
    @ApiResponse(responseCode = "202", description = "Request accepted")
    void createAnimal(AnimalDto animalDto);
}
