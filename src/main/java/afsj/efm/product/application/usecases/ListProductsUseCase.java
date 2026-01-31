package afsj.efm.product.application.usecases;

import afsj.efm.product.application.dtos.ProductResponse;
import afsj.efm.product.application.mappers.ProductMapper;
import afsj.efm.product.infrastructure.persistence.ProductJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ListProductsUseCase {

   private final ProductJpaRepository repository;

   public ListProductsUseCase(ProductJpaRepository repository) {
      this.repository = repository;
   }

   @Transactional(readOnly = true)
   public List<ProductResponse> execute() {
      return ProductMapper.toDtoList(repository.findAll());
   }
}
