package com.hiberus.adoptionskafka.kafka.lambdas;

import com.hiberus.adoptionskafka.avro.InstitutionAnimalsValue;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class InitializerAnimalsList implements org.apache.kafka.streams.kstream.Initializer<InstitutionAnimalsValue> {
    @Override
    public InstitutionAnimalsValue apply() {
        return InstitutionAnimalsValue.newBuilder()
                .setAnimals(new ArrayList<>())
                .build();
    }
}