package afsj.efm.category.domain.entities;

import afsj.efm.category.domain.exceptions.InvalidCategoryException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CategoryTest {

   @Test
   @DisplayName("Should create category with valid name")
   void createCategory() {
      var category = new Category("category");
      Assertions.assertEquals("category", category.getName());
   }

   @Test
   @DisplayName("Should throw error when name is null")
   void nameIsNull() {
      Assertions.assertThrows(InvalidCategoryException.class,
              () -> new Category(null));
   }

   @Test
   @DisplayName("Should throw error when name is blank")
   void nameIsBlank() {
      Assertions.assertThrows(InvalidCategoryException.class,
              () -> new Category("   "));
   }

   @Test
   @DisplayName("Should throw error when name is shorter than 3 characters")
   void nameIsTooShort() {
      Assertions.assertThrows(InvalidCategoryException.class,
              () -> new Category("ca"));
   }

   @Test
   @DisplayName("Should throw error when name is longer than 50 characters")
   void nameIsTooLong() {
      Assertions.assertThrows(InvalidCategoryException.class,
              () -> new Category("a".repeat(51)));
   }
}