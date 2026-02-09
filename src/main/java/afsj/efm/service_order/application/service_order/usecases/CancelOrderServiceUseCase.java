package afsj.efm.service_order.application.service_order.usecases;

import afsj.efm.service_order.infrastructure.persistence.ServiceOrderJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class CancelOrderServiceUseCase {

   private final ServiceOrderJpaRepository serviceOrderJpaRepository;

   public CancelOrderServiceUseCase(ServiceOrderJpaRepository serviceOrderJpaRepository) {
      this.serviceOrderJpaRepository = serviceOrderJpaRepository;
   }

   @Transactional
   public void execute(Long id){
      var serviceOrder = serviceOrderJpaRepository.findById(id)
              .orElseThrow(IllegalArgumentException::new);

      serviceOrder.cancel(LocalDate.now());
   }
}
