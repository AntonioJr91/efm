package afsj.efm.product.application.service;

import afsj.efm.category.application.errors.CategoryConflicts;
import afsj.efm.category.application.errors.CategoryNotFound;
import afsj.efm.category.infrastructure.persistence.CategoryJpaRepository;
import afsj.efm.product.application.dtos.ProductRequest;
import afsj.efm.product.application.dtos.ProductResponse;
import afsj.efm.product.application.dtos.StockUpdateResponse;
import afsj.efm.product.application.errors.ProductConflicts;
import afsj.efm.product.application.errors.ProductNotFound;
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
   private final CategoryJpaRepository categoryRepository;

   public ProductApplicationService(ProductJpaRepository repository, CategoryJpaRepository categoryRepository) {
      this.repository = repository;
      this.categoryRepository = categoryRepository;
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
      var category = categoryRepository.findById(request.categoryId())
              .orElseThrow(() -> CategoryNotFound.byId(request.categoryId()));

      repository.findByName(request.name()).ifPresent(product -> {
         throw ProductConflicts.nameAlreadyExists(request.name());
      });

      Product newProduct = new Product(request.name(), request.stock(), request.unitOfMeasure(), category);

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
      if (!repository.existsById(id)) throw ProductNotFound.byId(id);
      repository.deleteById(id);
   }

   private Product findProductById(Long id) {
      return repository.findById(id)
              .orElseThrow(() -> ProductNotFound.byId(id));
   }
}
