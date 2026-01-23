package afsj.efm.category.infrastructure.persistence;

import afsj.efm.category.domain.entities.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest
class CategoryJpaRepositoryTest {

   @Autowired
   private CategoryJpaRepository repository;

   @Test
   @DisplayName("Should save category when it is valid")
   void saveCategory() {
      Category category = new Category("category");

      Category saved = repository.save(category);

      Assertions.assertNotNull(saved.getId());
      Assertions.assertEquals("category", saved.getName());
   }

   @Test
   @DisplayName("Should find category by name")
   void findCategoryByName() {
      repository.save(new Category("category"));

      var result = repository.findByName("category");

      Assertions.assertTrue(result.isPresent());
      Assertions.assertEquals("category", result.get().getName());
   }

   @Test
   @DisplayName("Should return empty when name does not exist")
   void returnEmptyWhenNameDoesNotExist() {
      var result = repository.findByName("unknown");

      Assertions.assertTrue(result.isEmpty());
   }

   @Test
   @DisplayName("Should throw error when category name is duplicated")
   void notAllowDuplicate() {
      repository.saveAndFlush(new Category("category"));

      Assertions.assertThrows(
              DataIntegrityViolationException.class,
              () -> repository.saveAndFlush(new Category("category"))
      );
   }
}
