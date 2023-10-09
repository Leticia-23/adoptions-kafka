package com.hiberus.adoptionskafka.services.impl;

import com.hiberus.adoptionskafka.avro.InstitutionValue;
import com.hiberus.adoptionskafka.avro.InstitutionKey;
import com.hiberus.adoptionskafka.dto.InstitutionDto;
import com.hiberus.adoptionskafka.mappers.InstitutionMapper;
import com.hiberus.adoptionskafka.services.InstitutionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;

@Service
@Slf4j
public class InstitutionServiceImpl implements InstitutionService {

    @Value("${environment.institutions-topic}")
    private String institutionTopic;
    @Autowired
    InstitutionMapper institutionMapper;

    @Autowired
    private KafkaTemplate<InstitutionKey, InstitutionValue> kafkaTemplate;

    @Override
    public void createInstitution(InstitutionDto institution) {
        if (institution == null) {
            return;
        }

        String uuid = UUID.randomUUID().toString();

        InstitutionKey animalKey = InstitutionKey.newBuilder().setId(uuid).build();
        InstitutionValue animalValue = institutionMapper.dtoToEntity(institution, uuid);

        log.info("Sending institution to animas topic");
        kafkaTemplate.send(institutionTopic,animalKey,animalValue);
    }
}
