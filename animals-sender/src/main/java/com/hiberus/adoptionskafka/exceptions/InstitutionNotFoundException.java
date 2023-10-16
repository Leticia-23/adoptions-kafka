package com.hiberus.adoptionskafka.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InstitutionNotFoundException extends Exception {
    public InstitutionNotFoundException(String message) {
        super(message);
    }
}
