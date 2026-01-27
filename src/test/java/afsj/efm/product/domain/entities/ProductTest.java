package afsj.efm.product.domain.entities;

import afsj.efm.product.domain.enums.UnitOfMeasure;
import afsj.efm.product.domain.exceptions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductTest {

   String name = "semente";
   int stock = 100;
   UnitOfMeasure unit = UnitOfMeasure.UNIT;

   @Test
   @DisplayName("Should create a valid product")
   void createProduct() {
      Product product = new Product("semente", 100, UnitOfMeasure.UNIT);

      Assertions.assertEquals(name, product.getName());
      Assertions.assertEquals(stock, product.getAvailableStock());
      Assertions.assertEquals(unit, product.getUnitOfMeasure());
   }

   @Test
   @DisplayName("Should throw error when name is null")
   void nameIsNull() {
      Assertions.assertThrows(InvalidProductNameException.class,
              () -> new Product(null, stock, unit));
   }

   @Test
   @DisplayName("Should throw error when name is blank")
   void nameIsBlank() {
      Assertions.assertThrows(InvalidProductNameException.class,
              () -> new Product("   ", stock, unit));
   }

   @Test
   @DisplayName("Should throw error when name is shorter than 3 characters")
   void nameIsShorter() {
      Assertions.assertThrows(InvalidProductNameException.class,
              () -> new Product("a", stock, unit));
   }

   @Test
   @DisplayName("Should throw error when name is longer than 50 characters")
   void nameIsLonger() {
      Assertions.assertThrows(InvalidProductNameException.class,
              () -> new Product("a".repeat(51), stock, unit));
   }

   @Test
   @DisplayName("Should throw error when unit of measure is null")
   void unitOfMeasureIsNull() {
      Assertions.assertThrows(UnitOfMeasureRequiredException.class,
              () -> new Product(name, stock, null));
   }

   @Test
   @DisplayName("Should set createdAt when product is created")
   void createdAtValid() {
      Product product = new Product(name, stock, unit);

      Assertions.assertNotNull(product.getCreatedAt());
   }

   @Test
   @DisplayName("Should throw error when initial stock is negative")
   void initialStockIsNegative() {
      Assertions.assertThrows(InvalidInitialStockException.class,
              () -> new Product(name, -10, unit));
   }

   @Test
   @DisplayName("Should throw error when movement quantity is negative")
   void movementStockIsNegative() {
      Product product = new Product(name, stock, unit);

      Assertions.assertThrows(InvalidMovementQuantityException.class,
              () -> product.increaseStock(-1));

      Assertions.assertThrows(InvalidMovementQuantityException.class,
              () -> product.decreaseStock(-1));

   }

   @Test
   @DisplayName("Should throw error when stock is insufficient")
   void stockIsInsufficient() {
      Product product = new Product(name, stock, unit);
      Assertions.assertThrows(InsufficientStockException.class,
              () -> product.decreaseStock(stock + 10));
   }

   @Test
   @DisplayName("Should increase stock and register IN movement")
   void increaseStock() {
      Product product = new Product(name, stock, unit);

      product.increaseStock(10);

      Assertions.assertEquals(stock + 10, product.getAvailableStock());
      Assertions.assertEquals(1, product.getStockMovements().size());
   }

   @Test
   @DisplayName("Should throw error when movement quantity is zero")
   void movementQuantityIsZero() {
      Product product = new Product(name, stock, unit);

      Assertions.assertThrows(InvalidMovementQuantityException.class,
              () -> product.increaseStock(0));
   }

   @Test
   @DisplayName("Should not allow external modification of stock movements")
   void stockMovementShouldBeImmutable() {
      Product product = new Product(name, stock, unit);

      product.increaseStock(10);

      Assertions.assertThrows(UnsupportedOperationException.class,
              () -> product.getStockMovements().add(null));
   }
}
