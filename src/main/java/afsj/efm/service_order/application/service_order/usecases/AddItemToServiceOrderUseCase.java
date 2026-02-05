package afsj.efm.service_order.application.service_order.usecases;

import afsj.efm.product.domain.entities.Product;
import afsj.efm.product.infrastructure.persistence.ProductJpaRepository;
import afsj.efm.service_order.application.service_order.dtos.AddItemToServiceOrderRequest;
import afsj.efm.service_order.application.service_order.dtos.ServiceOrderDetailResponse;
import afsj.efm.service_order.application.service_order.mappers.ServiceOrderMapper;
import afsj.efm.service_order.domain.entities.ServiceOrder;
import afsj.efm.service_order.infrastructure.persistence.ServiceOrderJpaRepository;
import org.springframework.stereotype.Service;

@Service
public class AddItemToServiceOrderUseCase {

   private final ServiceOrderJpaRepository serviceOrderJpaRepository;
   private final ProductJpaRepository productJpaRepository;

   public AddItemToServiceOrderUseCase(ServiceOrderJpaRepository serviceOrderJpaRepository, ProductJpaRepository productJpaRepository) {
      this.serviceOrderJpaRepository = serviceOrderJpaRepository;
      this.productJpaRepository = productJpaRepository;
   }

   public ServiceOrderDetailResponse execute(AddItemToServiceOrderRequest request) {
      ServiceOrder serviceOrder = serviceOrderJpaRepository.findById(request.serviceOrderId())
              .orElseThrow(IllegalArgumentException::new);

      Product product = productJpaRepository.findById(request.productId())
              .orElseThrow(IllegalArgumentException::new);

      serviceOrder.addItem(product, request.quantity());

      return ServiceOrderMapper.toDtoDetail(serviceOrder);
   }
}
