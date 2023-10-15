package com.hiberus.adoptionskafka.controlers;

import com.hiberus.adoptionskafka.dto.AnimalDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


public interface AnimalControler {

    @Operation(summary = "Create animal")
    @ApiResponse(responseCode = "202", description = "Request accepted")
    void createAnimal(AnimalDto animalDto);

    @Operation(summary = "Modify animal")
    @ApiResponse(responseCode = "202", description = "Request accepted")
    void modifyAnimal(String idAnimal, AnimalDto animalDto);

    @Operation(summary = "Delete animal")
    @ApiResponse(responseCode = "202", description = "Request accepted")
    void deleteAnimal(String idAnimal, AnimalDto animalDto);
}
