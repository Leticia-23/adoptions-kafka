package com.hiberus.adoptionskafka.kafka.lambdas;

import java.util.stream.Collectors;

import com.hiberus.adoptionskafka.avro.Animal;
import com.hiberus.adoptionskafka.avro.InstitutionAnimalValue;
import com.hiberus.adoptionskafka.avro.InstitutionAnimalsValue;
import com.hiberus.adoptionskafka.avro.InstitutionKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AggregatorInstitutionsAnimals implements org.apache.kafka.streams.kstream.Aggregator<InstitutionKey, InstitutionAnimalValue, InstitutionAnimalsValue> {

    @Override
    public InstitutionAnimalsValue apply(InstitutionKey institutionKey,
                                         InstitutionAnimalValue institutionAnimalValue,
                                         InstitutionAnimalsValue institutionAnimalsValue) {

        log.info("Aggregator before institutionAnimalsValue");

        institutionAnimalsValue = InstitutionAnimalsValue.newBuilder()
                .setIdInstitution(institutionAnimalValue.getIdInstitution())
                .setName(institutionAnimalValue.getName())
                .setEmail(institutionAnimalValue.getEmail())
                .setAddress(institutionAnimalValue.getAddress())
                .setPhoneNumber(institutionAnimalValue.getPhoneNumber())
                .setWebURL(institutionAnimalValue.getWebURL())
                .setInformation(institutionAnimalValue.getInformation())
                // Remove duplicate animals
                .setAnimals(
                        institutionAnimalsValue.getAnimals()
                        .stream()
                        .filter(c -> !institutionAnimalValue.getAnimal().getIdAnimal().equals(c.getIdAnimal()))
                                .collect(Collectors.toList())).build();

        // Control thar institution have an animal and not only is a institution without animal
        if (institutionAnimalValue.getAnimal() != null) {
            institutionAnimalsValue.getAnimals().add(createAnimal(institutionAnimalValue));
        }

        log.info("Aggregator after institutionAnimalsValue add");

        return institutionAnimalsValue;
    }


    private Animal createAnimal(InstitutionAnimalValue institutionAnimalValue) {

        return Animal.newBuilder()
                .setIdAnimal(institutionAnimalValue.getAnimal().getIdAnimal())
                .setAnimalName(institutionAnimalValue.getAnimal().getAnimalName())
                .setSpecie(institutionAnimalValue.getAnimal().getSpecie())
                .setBreed(institutionAnimalValue.getAnimal().getBreed())
                .setSex(institutionAnimalValue.getAnimal().getSex())
                .setWeight(institutionAnimalValue.getAnimal().getWeight())
                .setSize(institutionAnimalValue.getAnimal().getSize())
                .setColor(institutionAnimalValue.getAnimal().getColor())
                .setDangerous(institutionAnimalValue.getAnimal().getDangerous())
                .setSterile(institutionAnimalValue.getAnimal().getSterile())
                .setAdopted(institutionAnimalValue.getAnimal().getAdopted())
                .build();
    }
}
