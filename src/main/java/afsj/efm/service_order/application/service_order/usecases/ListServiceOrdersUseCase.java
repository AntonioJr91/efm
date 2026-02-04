package afsj.efm.service_order.application.service_order.usecases;

import afsj.efm.service_order.application.service_order.dtos.ServiceOrderResponse;
import afsj.efm.service_order.application.service_order.mappers.ServiceOrderMapper;
import afsj.efm.service_order.infrastructure.persistence.ServiceOrderJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ListServiceOrdersUseCase {

   private final ServiceOrderJpaRepository repository;

   public ListServiceOrdersUseCase(ServiceOrderJpaRepository repository) {
      this.repository = repository;
   }

   @Transactional(readOnly = true)
   public List<ServiceOrderResponse> execute() {
      return ServiceOrderMapper.toDtoList(repository.findAll());
   }
}
