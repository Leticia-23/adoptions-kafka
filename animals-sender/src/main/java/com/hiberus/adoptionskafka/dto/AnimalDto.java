package com.hiberus.adoptionskafka.dto;

import lombok.Data;

@Data
public class AnimalDto {
    String name;
    String specie;
    String breed;
    String sex;
    Float weight;
    String color;
    Boolean dangerous;
    Boolean sterile;
    Boolean adopted;
    String idInstitution;
}
