package afsj.efm.service_order.application.service_order.usecases;

import afsj.efm.product.infrastructure.persistence.ProductJpaRepository;
import afsj.efm.service_order.application.service_order.errors.ServiceOrderNotFound;
import afsj.efm.service_order.domain.entities.ServiceOrder;
import afsj.efm.service_order.infrastructure.persistence.ServiceOrderJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class RemoveToServiceOrderItemUseCase {

   private final ServiceOrderJpaRepository serviceOrderJpaRepository;

   public RemoveToServiceOrderItemUseCase(
           ServiceOrderJpaRepository serviceOrderJpaRepository,
           ProductJpaRepository productJpaRepository
   ) {
      this.serviceOrderJpaRepository = serviceOrderJpaRepository;
   }

   @Transactional
   public void execute(Long orderId, UUID itemId) {
      ServiceOrder serviceOrder = serviceOrderJpaRepository.findById(orderId)
              .orElseThrow(ServiceOrderNotFound::byId);

      serviceOrder.getItems().forEach(item -> {
         item.getProduct().increaseStock(item.getQuantity());
      });

      serviceOrder.removeItem(itemId);
   }
}
