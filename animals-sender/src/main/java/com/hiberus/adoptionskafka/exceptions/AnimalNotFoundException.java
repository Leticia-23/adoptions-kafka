package com.hiberus.adoptionskafka.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AnimalNotFoundException extends Exception {
    public AnimalNotFoundException(String message) {
        super(message);
    }
}
