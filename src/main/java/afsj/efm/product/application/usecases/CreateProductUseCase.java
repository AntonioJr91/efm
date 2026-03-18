package afsj.efm.product.application.usecases;

import afsj.efm.category.application.errors.CategoryNotFound;
import afsj.efm.category.infrastructure.persistence.CategoryJpaRepository;
import afsj.efm.product.application.dtos.ProductRequest;
import afsj.efm.product.application.dtos.ProductResponse;
import afsj.efm.product.application.errors.ProductConflicts;
import afsj.efm.product.application.mappers.ProductMapper;
import afsj.efm.product.domain.entities.Product;
import afsj.efm.product.infrastructure.persistence.ProductJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateProductUseCase {

   private final ProductJpaRepository repository;
   private final CategoryJpaRepository categoryRepository;

   public CreateProductUseCase(ProductJpaRepository repository, CategoryJpaRepository categoryRepository) {
      this.repository = repository;
      this.categoryRepository = categoryRepository;
   }

   @Transactional
   public ProductResponse execute(ProductRequest request) {
      var category = categoryRepository.findById(request.categoryId())
              .orElseThrow(CategoryNotFound::byId);

      repository.findByName(request.name()).ifPresent(product -> {
         throw ProductConflicts.nameAlreadyExists();
      });

      Product newProduct = ProductMapper.toEntity(request, category);
      System.out.println(newProduct);

      Product saved = repository.save(newProduct);
      return ProductMapper.toDto(saved);
   }
}
