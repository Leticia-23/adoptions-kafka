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
    public void createInstitution(InstitutionDto institutionDto) {
        if (institutionDto == null) {
            return;
        }

        String uuid = UUID.randomUUID().toString();

        InstitutionKey institutionKey = InstitutionKey.newBuilder().setId(uuid).build();
        InstitutionValue institutionValue = institutionMapper.dtoToEntity(institutionDto, uuid);

        log.info("Sending institution to institutions topic");
        kafkaTemplate.send(institutionTopic,institutionKey,institutionValue);
    }

    @Override
    public void updateInstitution(String idInstitution, InstitutionDto institutionDto) {
        if (institutionDto == null) {
            return;
        }

        InstitutionKey institutionKey = InstitutionKey.newBuilder().setId(idInstitution).build();
        InstitutionValue institutionValue = institutionMapper.dtoToEntity(institutionDto, idInstitution);

        log.info("Sending institution updated to institutions topic");
        kafkaTemplate.send(institutionTopic,institutionKey,institutionValue);

    }


}
