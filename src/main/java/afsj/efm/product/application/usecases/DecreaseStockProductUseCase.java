package afsj.efm.product.application.usecases;

import afsj.efm.product.application.dtos.StockUpdateResponse;
import afsj.efm.product.application.errors.ProductNotFound;
import afsj.efm.product.application.mappers.StockMapper;
import afsj.efm.product.domain.entities.Product;
import afsj.efm.product.infrastructure.persistence.ProductJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DecreaseStockProductUseCase {

   private final ProductJpaRepository repository;

   public DecreaseStockProductUseCase(ProductJpaRepository repository) {
      this.repository = repository;
   }

   @Transactional
   public StockUpdateResponse execute(Long id, int quantity) {
      Product product = repository.findById(id)
              .orElseThrow(ProductNotFound::byId);

      product.decreaseStock(quantity);

      return StockMapper.toResponse(product);
   }
}
