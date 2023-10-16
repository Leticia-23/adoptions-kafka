package com.hiberus.adoptionskafka.Exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AdoptionsMicroUnavailable extends Exception {
    public AdoptionsMicroUnavailable(String message) {
        super(message);
    }
}
