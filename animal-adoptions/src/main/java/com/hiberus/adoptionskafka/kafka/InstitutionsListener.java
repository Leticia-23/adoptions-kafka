package com.hiberus.adoptionskafka.kafka;

import com.hiberus.adoptionskafka.avro.InstitutionAnimalsValue;
import com.hiberus.adoptionskafka.avro.InstitutionKey;
import com.hiberus.adoptionskafka.mappers.AdoptionsMapper;
import com.hiberus.adoptionskafka.services.AdoptionsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class InstitutionsListener {
    @Autowired
    AdoptionsService adoptionsService;

    @Autowired
    AdoptionsMapper adoptionsMapper;

    @Bean
    public Consumer<KStream<InstitutionKey, InstitutionAnimalsValue>> process() {
        return claseKStream -> claseKStream
                .peek((k, v) -> log.info("[animal-adoptions listener] Received message with key: {} and value {}", k, v))
                .peek((k, v) -> {
                    adoptionsService.saveInstitution(adoptionsMapper.avroToModel(v));
                });
    }
}
