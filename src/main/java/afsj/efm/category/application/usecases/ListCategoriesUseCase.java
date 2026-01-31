package afsj.efm.category.application.usecases;

import afsj.efm.category.application.dtos.CategoryResponse;
import afsj.efm.category.application.mappers.CategoryMapper;
import afsj.efm.category.infrastructure.persistence.CategoryJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ListCategoriesUseCase {

   private final CategoryJpaRepository repository;

   public ListCategoriesUseCase(CategoryJpaRepository repository) {
      this.repository = repository;
   }

   @Transactional(readOnly = true)
   public List<CategoryResponse> execute() {
      return CategoryMapper.toDtoList(repository.findAll());
   }
}
