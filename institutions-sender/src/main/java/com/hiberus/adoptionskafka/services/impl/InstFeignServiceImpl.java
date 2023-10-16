package com.hiberus.adoptionskafka.services.impl;

import com.hiberus.adoptionskafka.Exceptions.AdoptionsMicroUnavailable;
import com.hiberus.adoptionskafka.Exceptions.InstitutionNotFoundException;
import com.hiberus.adoptionskafka.clients.InstitutionClient;
import com.hiberus.adoptionskafka.dto.InstitutionDto;
import com.hiberus.adoptionskafka.services.InstFeignService;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.ConnectException;
import java.net.UnknownHostException;

@Service
@Slf4j
@AllArgsConstructor
public class InstFeignServiceImpl implements InstFeignService {

    private final InstitutionClient institutionClient;

    @CircuitBreaker(name = "animal-adoptions", fallbackMethod = "fallBackCheckInstitution")
    public ResponseEntity<InstitutionDto> checkInstitution(String idInstitution) throws InstitutionNotFoundException,  AdoptionsMicroUnavailable {
            return institutionClient.getInstitutionById(idInstitution);
    }

   private ResponseEntity<InstitutionDto> fallBackCheckInstitution(String idInstitution, Throwable throwable) throws Throwable {
        log.error("Exception Feign Client: " + throwable.getMessage());

        Throwable rootCause = getRootCause(throwable);

        if (rootCause instanceof FeignException.NotFound) {
            throw new InstitutionNotFoundException("Institution not found in DataBase");
        } else if (rootCause instanceof ConnectException) {
            throw new AdoptionsMicroUnavailable("Not connection with animal-adoptions microservice");
        } else if (rootCause instanceof UnknownHostException) {
            throw new AdoptionsMicroUnavailable("animal-adoptions microservice unknown");
        } else {
            throw throwable;
        }

    }

    private Throwable getRootCause(Throwable throwable) {
        Throwable rootCause = throwable;
        while (rootCause.getCause() != null) {
            rootCause = rootCause.getCause();
        }
        return rootCause;
    }
}
