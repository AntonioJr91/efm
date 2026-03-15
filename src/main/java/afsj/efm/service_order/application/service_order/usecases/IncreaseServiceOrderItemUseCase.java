package afsj.efm.service_order.application.service_order.usecases;

import afsj.efm.product.application.errors.ProductNotFound;
import afsj.efm.product.infrastructure.persistence.ProductJpaRepository;
import afsj.efm.service_order.application.service_order.dtos.ServiceOrderDetailResponse;
import afsj.efm.service_order.application.service_order.errors.ServiceOrderNotFound;
import afsj.efm.service_order.application.service_order.mappers.ServiceOrderMapper;
import afsj.efm.service_order.domain.entities.ServiceOrder;
import afsj.efm.service_order.infrastructure.persistence.ServiceOrderJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class IncreaseServiceOrderItemUseCase {

   private final ServiceOrderJpaRepository repository;
   private final ProductJpaRepository productRepository;

   public IncreaseServiceOrderItemUseCase(
           ServiceOrderJpaRepository repository,
           ProductJpaRepository productRepository
   ) {
      this.repository = repository;
      this.productRepository = productRepository;
   }

   @Transactional()
   public ServiceOrderDetailResponse execute(Long orderId, UUID itemId, int quantity) {
      ServiceOrder serviceOrder = repository.findById(orderId)
              .orElseThrow(ServiceOrderNotFound::byId);

      serviceOrder.increaseItemQuantity(itemId, quantity);

      var item = serviceOrder.getItems().stream()
              .filter(i -> i.getId().equals(itemId))
              .findFirst()
              .orElseThrow(ProductNotFound::byId);

      var product = item.getProduct();
      product.decreaseStock(quantity);

      return ServiceOrderMapper.toDtoDetail(serviceOrder);
   }
}
