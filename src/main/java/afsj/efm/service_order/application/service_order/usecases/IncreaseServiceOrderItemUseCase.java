package afsj.efm.service_order.application.service_order.usecases;

import afsj.efm.service_order.application.service_order.dtos.ServiceOrderDetailResponse;
import afsj.efm.service_order.application.service_order.mappers.ServiceOrderMapper;
import afsj.efm.service_order.domain.entities.ServiceOrder;
import afsj.efm.service_order.infrastructure.persistence.ServiceOrderJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class IncreaseServiceOrderItemUseCase {

   private final ServiceOrderJpaRepository repository;

   public IncreaseServiceOrderItemUseCase(ServiceOrderJpaRepository repository) {
      this.repository = repository;
   }

   @Transactional()
   public ServiceOrderDetailResponse execute(Long orderId, UUID itemId, int quantity) {
      ServiceOrder serviceOrder = repository.findById(orderId)
              .orElseThrow(() -> new IllegalArgumentException("SERVICE_ORDER_NOT_FOUND"));

      serviceOrder.increaseItemQuantity(itemId, quantity);
      repository.save(serviceOrder);

      return ServiceOrderMapper.toDtoDetail(serviceOrder);
   }
}
