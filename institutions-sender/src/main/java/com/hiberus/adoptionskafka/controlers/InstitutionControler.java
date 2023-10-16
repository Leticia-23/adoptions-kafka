package com.hiberus.adoptionskafka.controlers;

import com.hiberus.adoptionskafka.dto.InstitutionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;


public interface InstitutionControler {

    @Operation(summary = "Create institution")
    @ApiResponse(responseCode = "202", description = "Request accepted")
    void createInstitution(InstitutionDto institutionDto);

    @Operation(summary = "Modify institution")
    @ApiResponse(responseCode = "202", description = "Request accepted")
    ResponseEntity<String> modifyInstitution(String idInstitution, InstitutionDto institutionDto);

    @Operation(summary = "Delete institution")
    @ApiResponse(responseCode = "202", description = "Request accepted")
    void deleteInstitution(String idInstitution);
}
