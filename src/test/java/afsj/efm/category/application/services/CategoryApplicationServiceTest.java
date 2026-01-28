package afsj.efm.category.application.services;

import afsj.efm.category.application.dtos.CategoryRequest;
import afsj.efm.category.application.dtos.CategoryResponse;
import afsj.efm.category.domain.entities.Category;
import afsj.efm.category.infrastructure.persistence.CategoryJpaRepository;
import afsj.efm.shared.application.exceptions.ConflictException;
import afsj.efm.shared.application.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryApplicationServiceTest {

   @Mock
   private CategoryJpaRepository repository;

   @InjectMocks
   private CategoryApplicationService service;

   @Test
   @DisplayName("Should return empty list when no categories exist")
   void returnEmptyList() {
      when(repository.findAll()).thenReturn(List.of());

      var result = service.listCategories();

      assertThat(result).isEmpty();
      verify(repository).findAll();
   }

   @Test
   @DisplayName("Should return a category list")
   void returnCategoryList() {
      var categories = List.of(
              new Category("semente"),
              new Category("veneno")
      );

      when(repository.findAll()).thenReturn(categories);

      var result = service.listCategories();

      assertThat(result)
              .hasSize(2)
              .extracting(CategoryResponse::name)
              .containsExactly("semente", "veneno");

   }

   @Test
   @DisplayName("Should return category when found by id")
   void returnCategoryById() {
      var category = new Category("semente");

      when(repository.findById(1L)).thenReturn(Optional.of(category));

      var result = service.findById(1L);

      assertThat(result.name()).isEqualTo("semente");
      verify(repository).findById(1L);

   }

   @Test
   @DisplayName("Should throw not found when category does not exist")
   void returnNotFound() {
      when(repository.findById(1L)).thenReturn(Optional.empty());

      assertThrows(ResourceNotFoundException.class, () -> service.findById(1L));
   }

   @Test
   @DisplayName("Should throw conflict when category name already exists")
   void nameConflict() {
      var request = new CategoryRequest("semente");

      when(repository.findByName("semente"))
              .thenReturn(Optional.of(new Category("semente")));

      assertThrows(ConflictException.class, () -> service.save(request));

      verify(repository, never()).save(any());
   }

   @Test
   @DisplayName("Should save category when name does not exist")
   void saveCategory() {
      var request = new CategoryRequest("semente");

      when(repository.findByName("semente"))
              .thenReturn(Optional.empty());

      when(repository.save(any(Category.class)))
              .thenAnswer(invocation -> invocation.getArgument(0));

      var result = service.save(request);

      assertThat(result.name()).isEqualTo("semente");
      verify(repository).save(any(Category.class));
   }

   @Test
   @DisplayName("Should delete category when exists")
   void deleteCategory() {
      when(repository.existsById(1L)).thenReturn(true);

      service.delete(1L);
      verify(repository).deleteById(1L);
   }

   @Test
   @DisplayName("Should throw not found when deleting  non existing category")
   void returnErrorWhenDeleteCategory() {
      when(repository.existsById(1L)).thenReturn(false);

      assertThrows(ResourceNotFoundException.class, () -> service.delete(1L));
      verify(repository, never()).deleteById(1L);
   }
}