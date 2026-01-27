package afsj.efm.product.infrastructure.persistence;

import afsj.efm.product.domain.entities.Product;
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
   @Autowired
   private ProductJpaRepository repository;

   @BeforeEach
   void setUp() {
      product = new Product("semente", 10, UnitOfMeasure.UNIT);
   }

   @Test
   @DisplayName("Should save product when it is valid")
   void saveProduct() {
      var saved = repository.save(product);

      Assertions.assertNotNull(saved.getId());
      Assertions.assertEquals(product.getName(), saved.getName());
   }

   @Test
   @DisplayName("Should find product by name")
   void findProductByName() {
      repository.save(product);

      var result = repository.findByName(product.getName());

      Assertions.assertTrue(result.isPresent());
      Assertions.assertEquals(product.getName(), result.get().getName());
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
      var product2 = new Product("milho", 20, UnitOfMeasure.UNIT);

      repository.saveAndFlush(product);

      Assertions.assertThrows(
              DataIntegrityViolationException.class,
              () -> repository.saveAndFlush(product2)
      );
   }
   
}