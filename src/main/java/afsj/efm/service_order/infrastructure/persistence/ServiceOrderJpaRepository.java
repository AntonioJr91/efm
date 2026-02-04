package afsj.efm.service_order.infrastructure.persistence;

import afsj.efm.service_order.domain.entities.ServiceOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceOrderJpaRepository extends JpaRepository<ServiceOrder, Long> {
}
