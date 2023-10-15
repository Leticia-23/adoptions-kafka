package com.hiberus.adoptionskafka.services.impl;

import com.hiberus.adoptionskafka.avro.AnimalKey;
import com.hiberus.adoptionskafka.avro.AnimalValue;
import com.hiberus.adoptionskafka.dto.AnimalDto;
import com.hiberus.adoptionskafka.mappers.AnimalMapper;
import com.hiberus.adoptionskafka.services.AnimalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;

@Service
@Slf4j
public class AnimalServiceImpl implements AnimalService {

    @Value("${environment.animals-topic}")
    private String animalTopic;
    @Autowired
    AnimalMapper animalMapper;

    @Autowired
    private KafkaTemplate<AnimalKey, AnimalValue> kafkaTemplate;

    @Override
    public void createAnimal(AnimalDto animalDto) {
        if (animalDto == null) {
            return;
        }

        String uuid = UUID.randomUUID().toString();

        AnimalKey animalKey = AnimalKey.newBuilder().setId(uuid).build();
        AnimalValue animalValue = animalMapper.dtoToEntity(animalDto, uuid);

        log.info("Sending animal to animas topic");
        kafkaTemplate.send(animalTopic,animalKey,animalValue);
    }

    @Override
    public void updateAnimal(String idAnimal, AnimalDto animalDto) {
        if (animalDto == null) {
            return;
        }

        AnimalKey animalKey = AnimalKey.newBuilder().setId(idAnimal).build();
        AnimalValue institutionValue = animalMapper.dtoToEntity(animalDto, idAnimal);

        log.info("Sending animal updated to animals topic");
        kafkaTemplate.send(animalTopic,animalKey,institutionValue);
    }

    @Override
    public void deleteAnimal(String idAnimal, AnimalDto animalDto) {
        if (animalDto == null) {
            return;
        }

        AnimalKey animalKey = AnimalKey.newBuilder().setId(idAnimal).build();
        AnimalValue institutionValue = animalMapper.dtoToEntity(animalDto, idAnimal);

        log.info("Sending animal deleted to animals topic");
        kafkaTemplate.send(animalTopic, animalKey, institutionValue);
    }
}
