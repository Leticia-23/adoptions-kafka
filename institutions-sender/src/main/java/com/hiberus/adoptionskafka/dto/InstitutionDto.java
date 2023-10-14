package com.hiberus.adoptionskafka.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InstitutionDto {
    String name;
    String email;
    String address;
    String phoneNumber;
    String webURL;
    String information;
    @Setter
    EventType eventType;
}
