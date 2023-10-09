package com.hiberus.adoptionskafka.services;

import com.hiberus.adoptionskafka.avro.AnimalKey;
import com.hiberus.adoptionskafka.avro.AnimalValue;
import com.hiberus.adoptionskafka.avro.AnimalWithSizeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AnimalSizeAssignerService {
    @Value("${environment.animals-size-topic}")
    private String animalsSizeTopic;

    @Autowired
    private KafkaTemplate<AnimalKey, AnimalWithSizeValue> kafkaTemplate;

    private String sizeDependsWeight(Float weight) {
        if (weight < 10.0f ) {
            return "Small";
        } else if (weight >= 10.0f && weight < 30.0f) {
            return "Medium";
        } else
            return "Big";
    }

    public void assignSize(AnimalKey key, AnimalValue value) {
        AnimalWithSizeValue animalWithSize = AnimalWithSizeValue.newBuilder()
                .setId(value.getId())
                .setName(value.getName())
                .setSpecie(value.getSpecie())
                .setBreed(value.getBreed())
                .setSex(value.getSex())
                .setWeight(value.getWeight())
                .setSize(sizeDependsWeight(value.getWeight()))
                .setColor(value.getColor())
                .setDangerous(value.getDangerous())
                .setSterile(value.getSterile())
                .setAdopted(value.getAdopted())
                .setIdInstitution(value.getIdInstitution())
                .build();

        kafkaTemplate.send(animalsSizeTopic, key, animalWithSize);
    }
}
