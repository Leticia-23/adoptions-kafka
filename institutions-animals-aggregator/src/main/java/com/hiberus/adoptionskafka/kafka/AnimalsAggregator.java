package com.hiberus.adoptionskafka.kafka;

import com.hiberus.adoptionskafka.avro.InstitutionAnimalValue;
import com.hiberus.adoptionskafka.avro.InstitutionAnimalsValue;
import com.hiberus.adoptionskafka.avro.InstitutionKey;
import com.hiberus.adoptionskafka.kafka.lambdas.AggregatorInstitutionsAnimals;
import com.hiberus.adoptionskafka.kafka.lambdas.InitializerAnimalsList;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;


@Configuration
@Slf4j
public class AnimalsAggregator {

    @Autowired
    AggregatorInstitutionsAnimals aggregatorInstitutionsAnimals;

    @Autowired
    InitializerAnimalsList initializerAnimalsList;

    @Bean
    public Function<KStream<InstitutionKey, InstitutionAnimalValue>, KStream<InstitutionKey, InstitutionAnimalsValue>> aggregateAnimals() {
        return institutionsStream -> institutionsStream
                .peek((k, v) -> log.info("[aggregateMessages] Received message with key: {} and value {}", k, v))
                .selectKey((k, v) -> InstitutionKey.newBuilder().setId(k.getId()).build())
                .groupByKey()
                .aggregate(initializerAnimalsList, aggregatorInstitutionsAnimals, Named.as("INSITUTIONS_ANIMALS"), Materialized.as("INSITUTIONS_ANIMALS"))
                .toStream()
                .peek((k, v) -> log.info("[aggregateMessages] Sending message with key: {} and value {}", k, v));
    }
}
