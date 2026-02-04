package afsj.efm.service_order.application.service_order.usecases;

import afsj.efm.service_order.infrastructure.persistence.ServiceOrderJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteServiceOrderUseCase {

   private final ServiceOrderJpaRepository repository;

   public DeleteServiceOrderUseCase(ServiceOrderJpaRepository repository) {
      this.repository = repository;
   }

   @Transactional
   public void execute(Long id) {
      if (!repository.existsById(id)) throw new IllegalArgumentException();
      repository.deleteById(id);
   }
}
