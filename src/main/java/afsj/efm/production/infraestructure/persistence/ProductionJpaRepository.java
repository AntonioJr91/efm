package afsj.efm.production.infraestructure.persistence;

import afsj.efm.production.domain.entities.Production;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionJpaRepository extends JpaRepository<Production, Long> {
}
