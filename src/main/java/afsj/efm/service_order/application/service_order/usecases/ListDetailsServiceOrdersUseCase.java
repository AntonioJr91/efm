package afsj.efm.service_order.application.service_order.usecases;

import afsj.efm.service_order.application.service_order.dtos.ServiceOrderDetailResponse;
import afsj.efm.service_order.application.service_order.dtos.ServiceOrderResponse;
import afsj.efm.service_order.application.service_order.mappers.ServiceOrderItemMapper;
import afsj.efm.service_order.application.service_order.mappers.ServiceOrderMapper;
import afsj.efm.service_order.infrastructure.persistence.ServiceOrderJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ListDetailsServiceOrdersUseCase {

   private final ServiceOrderJpaRepository repository;

   public ListDetailsServiceOrdersUseCase(ServiceOrderJpaRepository repository) {
      this.repository = repository;
   }

   @Transactional(readOnly = true)
   public List<ServiceOrderDetailResponse> execute() {
      return ServiceOrderMapper.toDtoListDetails(repository.findAll());
   }
}
