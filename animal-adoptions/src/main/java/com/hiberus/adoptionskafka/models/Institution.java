package com.hiberus.adoptionskafka.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "institutions")
@Entity
@Getter
public class Institution {

    @Id
    @Column
    private String idInstitution;
    @Setter
    @Column
    private String name;
    @Setter
    @Column
    private String email;
    @Setter
    @Column
    private String address;
    @Setter
    @Column
    private String phoneNumber;
    @Setter
    @Column
    private String webURL;
    @Setter
    @Column
    private String information;

    @Setter
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Animal> animals;

}
