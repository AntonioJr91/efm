package afsj.efm.product.application.usecases;

import afsj.efm.product.application.dtos.ProductResponse;
import afsj.efm.product.application.errors.ProductNotFound;
import afsj.efm.product.application.mappers.ProductMapper;
import afsj.efm.product.infrastructure.persistence.ProductJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetProductByIdUseCase {

   private final ProductJpaRepository repository;

   public GetProductByIdUseCase(ProductJpaRepository repository) {
      this.repository = repository;
   }

   @Transactional(readOnly = true)
   public ProductResponse execute(Long id) {
      return ProductMapper.toDto(repository.findById(id)
              .orElseThrow(() -> ProductNotFound.byId(id)));
   }
}
