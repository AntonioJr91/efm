package afsj.efm.product.application.usecases;

import afsj.efm.product.application.errors.ProductNotFound;
import afsj.efm.product.infrastructure.persistence.ProductJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteProductUseCase {

   private final ProductJpaRepository repository;

   public DeleteProductUseCase(ProductJpaRepository repository) {
      this.repository = repository;
   }

   @Transactional
   public void execute(Long id) {
      if (!repository.existsById(id)) throw ProductNotFound.byId();
      repository.deleteById(id);
   }
}
