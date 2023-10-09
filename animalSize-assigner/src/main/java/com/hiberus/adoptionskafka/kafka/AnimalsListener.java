package com.hiberus.adoptionskafka.kafka;

import com.hiberus.adoptionskafka.services.AnimalSizeAssignerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import com.hiberus.adoptionskafka.avro.AnimalValue;
import com.hiberus.adoptionskafka.avro.AnimalKey;

@Configuration
@Slf4j
public class AnimalsListener {

    @Autowired
    AnimalSizeAssignerService animalSizeAssignerService;

    @KafkaListener(topics = "animals")
    public void process(ConsumerRecord<AnimalKey, AnimalValue> animal) {
        log.info("Received message from topic {} in partition {} and offset {} with key: {}",
                animal.topic(), animal.partition(), animal.offset(), animal.key());

        animalSizeAssignerService.assignSize(animal.key(), animal.value());
    }
}
