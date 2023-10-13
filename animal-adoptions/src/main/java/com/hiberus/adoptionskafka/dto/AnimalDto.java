package com.hiberus.adoptionskafka.dto;

import lombok.Data;

@Data
public class AnimalDto {
    private String idAnimal;
    private String animalName;
    private String specie;
    private String breed;
    private String sex;
    private float  weight;
    private String size;
    private String color;
    private boolean dangerous;
    private boolean sterile;
    private boolean adopted;
}
