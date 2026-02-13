package afsj.efm.service_order.application.service_order.usecases;

import afsj.efm.service_order.application.service_order.dtos.ServiceOrderResponse;
import afsj.efm.service_order.application.service_order.errors.ServiceOrderNotFound;
import afsj.efm.service_order.application.service_order.mappers.ServiceOrderMapper;
import afsj.efm.service_order.infrastructure.persistence.ServiceOrderJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetServiceOrderByIdUseCase {

   private final ServiceOrderJpaRepository repository;

   public GetServiceOrderByIdUseCase(ServiceOrderJpaRepository repository) {
      this.repository = repository;
   }

   @Transactional(readOnly = true)
   public ServiceOrderResponse execute(Long id) {
      return ServiceOrderMapper.toDto(repository.findById(id)
              .orElseThrow(() -> ServiceOrderNotFound.byId(id)));
   }
}
