package afsj.efm.category.application.services;

import afsj.efm.category.application.dtos.CategoryRequest;
import afsj.efm.category.application.dtos.CategoryResponse;
import afsj.efm.category.application.errors.CategoryConflicts;
import afsj.efm.category.application.errors.CategoryNotFound;
import afsj.efm.category.application.mappers.CategoryMapper;
import afsj.efm.category.domain.entities.Category;
import afsj.efm.category.infrastructure.persistence.CategoryJpaRepository;
import afsj.efm.product.application.errors.ProductConflicts;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryApplicationService {

   private final CategoryJpaRepository repository;

   public CategoryApplicationService(CategoryJpaRepository repository) {
      this.repository = repository;
   }

   @Transactional(readOnly = true)
   public List<CategoryResponse> listCategories() {
      return CategoryMapper.toDtoList(repository.findAll());
   }

   @Transactional(readOnly = true)
   public CategoryResponse findById(Long id) {
      var category = findCategoryById(id);
      return CategoryMapper.toDto(category);
   }

   @Transactional
   public CategoryResponse save(CategoryRequest request) {
      repository.findByName(request.name())
              .ifPresent(c -> {
                 throw CategoryConflicts.nameAlreadyExists(request.name());
              });

      var category = new Category(request.name());

      repository.save(category);

      return CategoryMapper.toDto(category);
   }

   @Transactional
   public void delete(Long id) {
      if (repository.existsById(id)) throw CategoryNotFound.byId(id);
      repository.deleteById(id);
   }

   private Category findCategoryById(Long id) {
      return repository.findById(id).orElseThrow(() -> CategoryNotFound.byId(id));
   }
}
