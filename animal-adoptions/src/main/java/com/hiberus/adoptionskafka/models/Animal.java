package com.hiberus.adoptionskafka.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "animals")
@Entity
@Getter
public class Animal {

    @Id
    @Column
    private String idAnimal;
    @Setter
    @Column
    private String animalName;
    @Setter
    @Column
    private String specie;
    @Setter
    @Column
    private String breed;
    @Setter
    @Column
    private String sex;
    @Setter
    @Column
    private float  weight;
    @Setter
    @Column
    private String size;
    @Setter
    @Column
    private String color;
    @Setter
    @Column
    private boolean dangerous;
    @Setter
    @Column
    private boolean sterile;
    @Setter
    @Column
    private boolean adopted;
}
