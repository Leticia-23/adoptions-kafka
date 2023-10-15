package com.hiberus.adoptionskafka.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
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
    @Setter
    EventType eventType;
}
