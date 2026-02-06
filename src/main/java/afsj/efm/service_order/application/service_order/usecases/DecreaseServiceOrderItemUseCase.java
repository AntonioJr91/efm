package afsj.efm.service_order.application.service_order.usecases;

import afsj.efm.service_order.application.service_order.dtos.ServiceOrderDetailResponse;
import afsj.efm.service_order.application.service_order.mappers.ServiceOrderMapper;
import afsj.efm.service_order.domain.entities.ServiceOrder;
import afsj.efm.service_order.infrastructure.persistence.ServiceOrderJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DecreaseServiceOrderItemUseCase {

   private final ServiceOrderJpaRepository repository;

   public DecreaseServiceOrderItemUseCase(ServiceOrderJpaRepository repository) {
      this.repository = repository;
   }

   @Transactional()
   public ServiceOrderDetailResponse execute(Long orderId, Long itemId, int quantity) {
      ServiceOrder serviceOrder = repository.findById(orderId)
              .orElseThrow(() -> new IllegalArgumentException("SERVICE_ORDER_NOT_FOUND"));

      serviceOrder.decreaseItemQuantity(itemId, quantity);
      repository.save(serviceOrder);

      return ServiceOrderMapper.toDtoDetail(serviceOrder);
   }
}
