package com.hiberus.adoptionskafka.kafka;

import com.hiberus.adoptionskafka.avro.Animal;
import com.hiberus.adoptionskafka.avro.EventType;
import com.hiberus.adoptionskafka.avro.InstitutionAnimalsValue;
import com.hiberus.adoptionskafka.avro.InstitutionKey;
import com.hiberus.adoptionskafka.exceptions.InstitutionAlreadyExistsException;
import com.hiberus.adoptionskafka.exceptions.InstitutionNotFoundException;
import com.hiberus.adoptionskafka.mappers.AdoptionsMapper;
import com.hiberus.adoptionskafka.services.AdoptionsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.management.InstanceAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Configuration
@Slf4j
public class InstitutionsListener {
    @Autowired
    AdoptionsService adoptionsService;

    @Autowired
    AdoptionsMapper adoptionsMapper;

    @Bean
    public Consumer<KStream<InstitutionKey, InstitutionAnimalsValue>> process() {
        return adoptiosKStream -> adoptiosKStream
                .peek((k, v) -> log.info("[animal-adoptions listener] Received message with key: {} and value {}", k, v))
                .peek((k, v) -> {
                    if (v == null) {
                        log.info("[animal-adoptions] delete institution and its animals");
                        try {
                            adoptionsService.deleteInstitution(k.getId());
                        } catch (InstitutionNotFoundException e) {
                            log.error("InstitutionNotFoundException caught: {}", e.getMessage());
                        }
                    } else {
                        if (v.getEventType() == EventType.POST ) {
                            log.info("[animal-adoptions] create institution without animals");
                            try {
                                adoptionsService.saveInstitution(adoptionsMapper.avroToModel(v));
                            } catch (InstitutionAlreadyExistsException e) {
                                log.error("InstitutionAlreadyExistsException caught: {}", e.getMessage());
                            }
                        } else if (v.getEventType() == EventType.PUT) {

                            log.info("[animal-adoptions] update institution");

                            if (!v.getAnimals().isEmpty()) {
                                log.info("[animal-adoptions] update institution with animals");

                                // Filter animals that have been removed or will be removed
                                List<Animal> deleteAnimals = v.getAnimals()
                                        .stream()
                                        .filter(animal -> animal.getEventType() == EventType.DELETE)
                                        .toList();

                                // Delete the animals that must be deleted so that they are not saved
                                List<Animal> updatedAnimals = new ArrayList<>(v.getAnimals());
                                updatedAnimals.removeAll(deleteAnimals);
                                v.setAnimals(updatedAnimals);
                            }

                            try {
                                adoptionsService.updateInstitution(adoptionsMapper.avroToModel(v));
                            } catch (InstitutionNotFoundException e) {
                                log.error("InstitutionNotFoundException caught: {}", e.getMessage());
                            }

                        }
                    }
                });
    }
}
