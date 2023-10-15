package com.hiberus.adoptionskafka.services;

import com.hiberus.adoptionskafka.dto.AnimalDto;

public interface AnimalService {
    void createAnimal(AnimalDto animal);

    void updateAnimal(String idAnimal, AnimalDto animalDto);

    void deleteAnimal(String idAnimal, AnimalDto animalDto);
}
