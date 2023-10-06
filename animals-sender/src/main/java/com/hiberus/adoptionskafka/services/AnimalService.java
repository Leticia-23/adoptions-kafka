package com.hiberus.adoptionskafka.services;

import com.hiberus.adoptionskafka.dto.AnimalDto;

public interface AnimalService {
    void createAnimal(AnimalDto animal);
}
