package afsj.efm.category.application.usecases;

import afsj.efm.category.application.dtos.CategoryResponse;
import afsj.efm.category.application.errors.CategoryNotFound;
import afsj.efm.category.application.mappers.CategoryMapper;
import afsj.efm.category.infrastructure.persistence.CategoryJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetCategoryByIdUseCase {

   private final CategoryJpaRepository repository;

   public GetCategoryByIdUseCase(CategoryJpaRepository repository) {
      this.repository = repository;
   }

   @Transactional(readOnly = true)
   public CategoryResponse execute(Long id) {
      var category = repository.findById(id).orElseThrow(CategoryNotFound::byId);
      return CategoryMapper.toDto(category);
   }
}
