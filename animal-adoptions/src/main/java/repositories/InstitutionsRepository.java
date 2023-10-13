package repositories;

import com.hiberus.adoptionskafka.models.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstitutionsRepository extends JpaRepository<Institution,String> {
    Institution findByIdInstitution(String id);
}