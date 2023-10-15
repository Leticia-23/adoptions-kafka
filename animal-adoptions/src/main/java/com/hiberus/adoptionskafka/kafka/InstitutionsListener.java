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
                        try {
                            adoptionsService.deleteInstitution(k.getId());
                        } catch (InstitutionNotFoundException e) {
                            log.error("InstitutionNotFoundException caught: {}", e.getMessage());
                        }
                    } else {
                        if (v.getEventType() == EventType.POST ) {
                            try {
                                adoptionsService.saveInstitution(adoptionsMapper.avroToModel(v));
                            } catch (InstitutionAlreadyExistsException e) {
                                log.error("InstitutionAlreadyExistsException caught: {}", e.getMessage());
                            }
                        } else if (v.getEventType() == EventType.PUT) {

                            if (v.getAnimals().isEmpty()) {
                                try {
                                    adoptionsService.updateInstitution(adoptionsMapper.avroToModel(v));
                                } catch (InstitutionNotFoundException e) {
                                    log.error("InstitutionNotFoundException caught: {}", e.getMessage());
                                }
                            } else {
                                List<Animal> notDeleteAnimals = v.getAnimals()
                                        .stream()
                                        .filter(animal -> animal.getEventType() == EventType.POST || animal.getEventType() == EventType.PUT)
                                        .toList();

                                List<Animal> deleteAnimals = v.getAnimals()
                                        .stream()
                                        .filter(animal -> animal.getEventType() == EventType.DELETE)
                                        .toList();

                                if (!notDeleteAnimals.isEmpty()) {
                                    InstitutionAnimalsValue newValue = createInstitutionAnimalsValue(v, notDeleteAnimals);
                                    try {
                                        adoptionsService.updateInstitution(adoptionsMapper.avroToModel(newValue));
                                    } catch (InstitutionNotFoundException e) {
                                        log.error("InstitutionNotFoundException caught: {}", e.getMessage());
                                    }
                                }

                                if (!deleteAnimals.isEmpty()) {
                                    InstitutionAnimalsValue newValue = createInstitutionAnimalsValue(v, notDeleteAnimals);
                                    try {
                                        adoptionsService.deleteAnimalsFromInstitution(adoptionsMapper.avroToModel(newValue));
                                    } catch (InstitutionNotFoundException e) {
                                        log.error("InstitutionNotFoundException caught: {}", e.getMessage());
                                    }
                                }
                            }
                        }
                    }
                });
    }

    InstitutionAnimalsValue createInstitutionAnimalsValue(InstitutionAnimalsValue institutionAnimalsValue, List<Animal> animalsList) {
        return InstitutionAnimalsValue.newBuilder()
                .setIdInstitution(institutionAnimalsValue.getIdInstitution())
                .setName(institutionAnimalsValue.getName())
                .setEmail(institutionAnimalsValue.getEmail())
                .setAddress(institutionAnimalsValue.getAddress())
                .setPhoneNumber(institutionAnimalsValue.getPhoneNumber())
                .setWebURL(institutionAnimalsValue.getWebURL())
                .setInformation(institutionAnimalsValue.getInformation())
                .setEventType(institutionAnimalsValue.getEventType())
                .setAnimals(animalsList).build();
    }
}
