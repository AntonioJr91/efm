package afsj.efm.category.application.usecases;

import afsj.efm.category.application.dtos.CategoryRequest;
import afsj.efm.category.application.dtos.CategoryResponse;
import afsj.efm.category.application.errors.CategoryConflicts;
import afsj.efm.category.application.mappers.CategoryMapper;
import afsj.efm.category.domain.entities.Category;
import afsj.efm.category.infrastructure.persistence.CategoryJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateCategoryUseCase {

   private final CategoryJpaRepository repository;

   public CreateCategoryUseCase(CategoryJpaRepository repository) {
      this.repository = repository;
   }

   @Transactional
   public CategoryResponse execute(CategoryRequest request) {
      repository.findByName(request.name())
              .ifPresent(c -> {
                 throw CategoryConflicts.nameAlreadyExists(request.name());
              });

      var category = new Category(request.name());

      Category saved = repository.save(category);

      return CategoryMapper.toDto(saved);
   }
}
