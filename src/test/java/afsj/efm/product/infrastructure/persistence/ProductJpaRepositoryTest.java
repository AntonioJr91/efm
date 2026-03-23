package afsj.efm.product.infrastructure.persistence;

import afsj.efm.category.domain.entities.Category;
import afsj.efm.category.infrastructure.persistence.CategoryJpaRepository;
import afsj.efm.product.domain.entities.Product;
import afsj.efm.product.domain.enums.ProductOrigin;
import afsj.efm.product.domain.enums.UnitOfMeasure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest
class ProductJpaRepositoryTest {

   Product product;
   Category category;

   @Autowired
   private ProductJpaRepository repository;

   @Autowired
   private CategoryJpaRepository categoryJpaRepository;

   @BeforeEach
   void setUp() {
      category = categoryJpaRepository.save(new Category("other"));
      product = new Product("semente", 10, 0, UnitOfMeasure.UNIT, ProductOrigin.OWN_PRODUCTION, category);
   }

   @Test
   @DisplayName("Should save product when it is valid")
   void saveProduct() {
      var saved = repository.save(product);

      Assertions.assertNotNull(saved.getId());
      Assertions.assertEquals(product.getName(), saved.getName());
      Assertions.assertEquals(product.getCategory(), saved.getCategory());
   }

   @Test
   @DisplayName("Should find product by name")
   void findProductByName() {
      repository.save(product);

      var result = repository.findByName(product.getName());

      Assertions.assertTrue(result.isPresent());
      Assertions.assertEquals(product.getName(), result.get().getName());
      Assertions.assertEquals(product.getCategory(), result.get().getCategory());
   }

   @Test
   @DisplayName("Should return empty when name does not exist")
   void returnEmptyWhenNameDoesNotExist() {
      var result = repository.findByName("unknown");

      Assertions.assertTrue(result.isEmpty());
   }

   @Test
   @DisplayName("Should throw error when product name is duplicated")
   void notAllowDuplicate() {
      var product2 = new Product("semente", 20, 0, UnitOfMeasure.UNIT, ProductOrigin.OWN_PRODUCTION, category);

      repository.saveAndFlush(product);

      Assertions.assertThrows(
              DataIntegrityViolationException.class,
              () -> repository.saveAndFlush(product2)
      );
   }

}
