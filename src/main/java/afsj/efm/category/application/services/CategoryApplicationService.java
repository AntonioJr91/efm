package afsj.efm.category.application.services;

import afsj.efm.category.application.dtos.CategoryRequest;
import afsj.efm.category.application.dtos.CategoryResponse;
import afsj.efm.category.application.exceptions.ConflictException;
import afsj.efm.category.application.exceptions.ResourceNotFoundException;
import afsj.efm.category.application.mappers.CategoryMapper;
import afsj.efm.category.domain.entities.Category;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

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

   @Transactional(readOnly = true)
   public CategoryResponse findByName(String name) {
      var category = findCategoryByName(name);
      return CategoryMapper.toDto(category);
   }

   @Transactional
   public CategoryResponse save(@RequestBody @Valid CategoryRequest request) {
      repository.findByName(request.name())
              .ifPresent(c -> {
                 throw ConflictException.categoryName(request.name());
              });

      var category = new Category(request.name());

      repository.save(category);

      return CategoryMapper.toDto(category);
   }

   @Transactional
   public void delete(Long id) {
      var category = findCategoryById(id);
      repository.delete(category);
   }

   private Category findCategoryById(Long id) {
      return repository.findById(id).orElseThrow(() -> ResourceNotFoundException.byId(id));
   }

   private Category findCategoryByName(String name) {
      return repository.findByName(name)
              .orElseThrow(() -> ResourceNotFoundException.byName(name));
   }
}
