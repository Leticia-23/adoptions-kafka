package com.hiberus.adoptionskafka.dto;

import lombok.Data;

import java.util.List;

@Data
public class InstitutionDto {
    private String idInstitution;
    private String name;
    private String email;
    private String address;
    private String phoneNumber;
    private String webURL;
    private String information;
    private List<AnimalDto> animals;
}
