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
                    .toTable(Named.as("ANIMAL_INSTITUTION"), Materialized.as("ANIMAL_INSTITUTION"));

            return institutionsKTable.leftJoin(animalsKTable,
                            (institutionValue, animalValue) -> animalValue == null ?
                                  createLeftJoin(institutionValue) :
                                    createFullJoin(institutionValue, animalValue))
                    .toStream()
                    .peek((k, v) -> log.info("[joiner] Sending message with key: {} and value: {}", k, v));
        };
    }


    private InstitutionAnimalValue createLeftJoin(InstitutionValue institutionValue) {
        return  InstitutionAnimalValue.newBuilder()
                .setIdInstitution(institutionValue.getId())
                .setName(institutionValue.getName())
                .setEmail(institutionValue.getEmail())
                .setAddress(institutionValue.getAddress())
                .setPhoneNumber(institutionValue.getPhoneNumber())
                .setWebURL(institutionValue.getWebURL())
                .setInformation(institutionValue.getInformation())
                .setAnimal(null)
                .build();
    }

    private InstitutionAnimalValue createFullJoin(InstitutionValue institutionValue, AnimalValue animalValue) {

        Animal animal = Animal.newBuilder()
                .setIdAnimal(animalValue.getId())
                .setAnimalName(animalValue.getName())
                .setSpecie(animalValue.getSpecie())
                .setBreed(animalValue.getBreed())
                .setSex(animalValue.getSex())
                .setWeight(animalValue.getWeight())
                .setSize(animalValue.getSize())
                .setColor(animalValue.getColor())
                .setDangerous(animalValue.getDangerous())
                .setSterile(animalValue.getSterile())
                .setAdopted(animalValue.getAdopted())
                .build();

        return  InstitutionAnimalValue.newBuilder()
                .setIdInstitution(institutionValue.getId())
                .setName(institutionValue.getName())
                .setEmail(institutionValue.getEmail())
                .setAddress(institutionValue.getAddress())
                .setPhoneNumber(institutionValue.getPhoneNumber())
                .setWebURL(institutionValue.getWebURL())
                .setInformation(institutionValue.getInformation())
                .setAnimal(animal)
                .build();
    }
}
