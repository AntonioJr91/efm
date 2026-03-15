package afsj.efm.service_order.application.service_order.usecases;

import afsj.efm.service_order.application.service_order.dtos.ServiceOrderDetailResponse;
import afsj.efm.service_order.application.service_order.errors.ServiceOrderNotFound;
import afsj.efm.service_order.application.service_order.mappers.ServiceOrderMapper;
import afsj.efm.service_order.domain.entities.ServiceOrder;
import afsj.efm.service_order.infrastructure.persistence.ServiceOrderJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DecreaseServiceOrderItemUseCase {

   private final ServiceOrderJpaRepository repository;

   public DecreaseServiceOrderItemUseCase(ServiceOrderJpaRepository repository) {
      this.repository = repository;
   }

   @Transactional()
   public ServiceOrderDetailResponse execute(Long orderId, UUID itemId, int quantity) {
      ServiceOrder serviceOrder = repository.findById(orderId)
              .orElseThrow(ServiceOrderNotFound::byId);

      serviceOrder.decreaseItemQuantity(itemId, quantity);

      var item = serviceOrder.getItems().stream()
              .filter(i -> i.getId().equals(itemId))
              .findFirst()
              .orElseThrow(ServiceOrderNotFound::byId);

       var product = item.getProduct();
       product.increaseStock(quantity);

      return ServiceOrderMapper.toDtoDetail(serviceOrder);
   }
}
