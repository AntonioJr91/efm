package afsj.efm.service_order.infrastructure.persistence;

import afsj.efm.service_order.domain.entities.ServiceOrder;
import afsj.efm.service_order.domain.entities.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceOrderJpaRepository extends JpaRepository<ServiceOrder, Long> {
   boolean existsByServiceTypeServiceTypeName(String serviceTypeName);
}
