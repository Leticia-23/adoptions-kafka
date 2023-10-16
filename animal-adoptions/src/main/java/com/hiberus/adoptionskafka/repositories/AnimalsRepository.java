package com.hiberus.adoptionskafka.repositories;

import com.hiberus.adoptionskafka.models.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalsRepository extends JpaRepository<Animal,String> {
}
