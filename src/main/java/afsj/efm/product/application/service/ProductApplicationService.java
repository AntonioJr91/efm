package afsj.efm.product.application.service;

import afsj.efm.product.application.dtos.ProductRequest;
import afsj.efm.product.application.dtos.ProductResponse;
import afsj.efm.product.application.dtos.StockUpdateResponse;
import afsj.efm.product.application.exceptions.ConflictException;
import afsj.efm.product.application.exceptions.ResourceNotFoundException;
import afsj.efm.product.application.mappers.ProductMapper;
import afsj.efm.product.application.mappers.StockMapper;
import afsj.efm.product.domain.entities.Product;
import afsj.efm.product.infrastructure.persistence.ProductJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductApplicationService {

   private final ProductJpaRepository repository;

   public ProductApplicationService(ProductJpaRepository repository) {
      this.repository = repository;
   }

   @Transactional(readOnly = true)
   public List<ProductResponse> listProducts() {
      return ProductMapper.toDtoList(repository.findAll());
   }

   @Transactional(readOnly = true)
   public ProductResponse findById(Long id) {
      return ProductMapper.toDto(findProductById(id));
   }

   @Transactional
   public ProductResponse save(ProductRequest request) {
      repository.findByName(request.name()).ifPresent(product -> {
         throw ConflictException.productName(request.name());
      });

      Product newProduct = new Product(request.name(), request.stock(), request.unitOfMeasure());

      Product saved = repository.save(newProduct);

      return ProductMapper.toDto(saved);
   }

   @Transactional
   public StockUpdateResponse increase(Long id, int quantity) {
      Product product = findProductById(id);

      product.increaseStock(quantity);

      return StockMapper.toResponse(product);
   }

   @Transactional
   public StockUpdateResponse decrease(Long id, int quantity) {
      Product product = findProductById(id);

      product.decreaseStock(quantity);

      return StockMapper.toResponse(product);
   }

   @Transactional
   public void delete(Long id) {
      if (!repository.existsById(id)) throw ResourceNotFoundException.byId(id);
      repository.deleteById(id);
   }

   private Product findProductById(Long id) {
      return repository.findById(id)
              .orElseThrow(() -> ResourceNotFoundException.byId(id));
   }
}
