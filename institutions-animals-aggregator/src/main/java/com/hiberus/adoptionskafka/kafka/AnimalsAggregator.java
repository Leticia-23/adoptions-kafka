package com.hiberus.adoptionskafka.kafka;

import com.hiberus.adoptionskafka.avro.InstitutionAnimalValue;
import com.hiberus.adoptionskafka.avro.InstitutionAnimalsValue;
import com.hiberus.adoptionskafka.avro.InstitutionKey;
import com.hiberus.adoptionskafka.kafka.lambdas.AggregatorInstitutionsAnimals;
import com.hiberus.adoptionskafka.kafka.lambdas.InitializerAnimalsList;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.BranchedKStream;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
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
        return institutionsStream -> {
            KStream<InstitutionKey, InstitutionAnimalsValue> nonNullRecords = institutionsStream
                    .filter((k, v) -> v != null)
                    .peek((k, v) -> log.info("[aggregateAnimals] Received message with key: {} and value {}", k, v))
                    .groupByKey()
                    .aggregate(initializerAnimalsList, aggregatorInstitutionsAnimals, Named.as("INSTITUTIONS_ANIMALS"), Materialized.as("INSTITUTIONS_ANIMALS"))
                    .toStream()
                    .peek((k, v) -> log.info("[aggregateAnimals] Sending message with key: {} and value {}", k, v));

            InstitutionAnimalsValue institutionAnimalsValue = null;

            KStream<InstitutionKey, InstitutionAnimalsValue> nullRecords = institutionsStream
                    .filter((k, v) -> v == null)
                    .peek((k, v) -> log.info("[aggregateAnimals] Received tombstone with key: {} and value {}", k, v))
                    .mapValues(v -> institutionAnimalsValue)
                    .peek((k, v) -> log.info("[aggregateAnimals] Sending tombstone with key: {} and value {}", k, v));

            return nonNullRecords.merge(nullRecords);
        };
    }
}
