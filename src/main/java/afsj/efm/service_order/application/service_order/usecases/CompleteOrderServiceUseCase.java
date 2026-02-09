package afsj.efm.service_order.application.service_order.usecases;

import afsj.efm.service_order.infrastructure.persistence.ServiceOrderJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class CompleteOrderServiceUseCase {

   private final ServiceOrderJpaRepository serviceOrderJpaRepository;

   public CompleteOrderServiceUseCase(ServiceOrderJpaRepository serviceOrderJpaRepository) {
      this.serviceOrderJpaRepository = serviceOrderJpaRepository;
   }

   @Transactional
   public void execute(Long id) {
      var serviceOrder = serviceOrderJpaRepository.findById(id)
              .orElseThrow(IllegalArgumentException::new);

      serviceOrder.complete(LocalDate.now());
   }
}
