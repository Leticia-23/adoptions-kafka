package adoptionskafka.kafka;


import com.hiberus.adoptionskafka.avro.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Named;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;

import java.util.function.BiFunction;

@Configuration
@Slf4j
public class InstitutionAnimalLeftJoin {

    @Bean
    public BiFunction<KStream<InstitutionKey, InstitutionValue>, KStream<AnimalKey, AnimalValue>, KStream<InstitutionKey, InstitutionAnimalValue>> joiner() {
        return (institutionsStream, animalsStream) -> {
            KTable<InstitutionKey, InstitutionValue> institutionsKTable = institutionsStream
                    .selectKey((k, v) -> InstitutionKey.newBuilder().setId(k.getId()).build())
                    .toTable(Named.as("INSTITUTION"), Materialized.as("INSTITUTION"));

            KTable<InstitutionKey, AnimalValue> animalsKTable = animalsStream
                    .selectKey((k, v) -> InstitutionKey.newBuilder().setId(v.getIdInstitution()).build())
                    .toTable(Named.as("ANIMAL_INSTITUTION"), Materialized.as("ANIMAL_INSTIUTION"));

            return institutionsKTable.leftJoin(animalsKTable,
                            (precioValue, promocionValue) -> promocionValue == null ?
                                    InstitutionAnimalValue.newBuilder()
                                            // TODO:  create value
                                            .build() :
                                    InstitutionAnimalValue.newBuilder()
                                            // TODO:  create value
                                            .build())
                    .toStream()
                    .peek((k, v) -> log.info("[joiner] Sending message with key: {} and value: {}", k, v));
        };
    }
}
