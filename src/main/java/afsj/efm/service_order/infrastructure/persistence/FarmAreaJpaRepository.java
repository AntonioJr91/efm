package afsj.efm.service_order.infrastructure.persistence;

import afsj.efm.service_order.domain.entities.FarmArea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FarmAreaJpaRepository extends JpaRepository<FarmArea, Long> {
   Optional<FarmArea> findByName(String name);
}
