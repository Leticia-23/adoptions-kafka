package com.hiberus.adoptionskafka.Exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InstitutionNotFoundException extends Exception {
    public InstitutionNotFoundException(String message) {
        super(message);
    }
}
