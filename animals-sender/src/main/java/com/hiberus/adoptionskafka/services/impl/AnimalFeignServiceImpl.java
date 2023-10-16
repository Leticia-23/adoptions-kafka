package com.hiberus.adoptionskafka.services.impl;

import com.hiberus.adoptionskafka.clients.AnimalClient;
import com.hiberus.adoptionskafka.dto.AnimalDto;
import com.hiberus.adoptionskafka.dto.InstitutionDto;
import com.hiberus.adoptionskafka.exceptions.AdoptionsMicroUnavailable;
import com.hiberus.adoptionskafka.exceptions.AnimalNotFoundException;
import com.hiberus.adoptionskafka.exceptions.InstitutionNotFoundException;
import com.hiberus.adoptionskafka.services.AnimalFeignService;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.ConnectException;
import java.net.UnknownHostException;

@Service
@AllArgsConstructor
@Slf4j
public class AnimalFeignServiceImpl implements AnimalFeignService {

        private final AnimalClient animalClient;

        @CircuitBreaker(name = "animal-adoptions",fallbackMethod = "fallBackCheckAnimal")
        @Override
        public ResponseEntity<AnimalDto> checkAnimal(String idAnimal) throws AnimalNotFoundException, AdoptionsMicroUnavailable {
            return animalClient.getAnimalById(idAnimal);
        }

        @CircuitBreaker(name = "institution-adoptions",fallbackMethod = "fallBackCheckInstitution")
        @Override
        public ResponseEntity<InstitutionDto> checkInstitution(String idInstitution) throws InstitutionNotFoundException,  AdoptionsMicroUnavailable {
            return animalClient.getInstitutionById(idInstitution);
        }

        private ResponseEntity<AnimalDto> fallBackCheckAnimal(String idAnimal, Throwable throwable) throws Throwable {
            log.error("Exception Feign Client: " + throwable.getMessage());

            Throwable rootCause = getRootCause(throwable);

            if (rootCause instanceof FeignException.NotFound) {
                throw new AnimalNotFoundException("Animal not found in DataBase");
            } else if (rootCause instanceof ConnectException) {
                throw new AdoptionsMicroUnavailable("Not connection with animal-adoptions microservice");
            } else if (rootCause instanceof UnknownHostException) {
                throw new AdoptionsMicroUnavailable("animal-adoptions microservice unknown");
            } else {
                throw throwable;
            }

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
