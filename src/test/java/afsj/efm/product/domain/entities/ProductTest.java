package afsj.efm.product.domain.entities;

import afsj.efm.category.domain.entities.Category;
import afsj.efm.product.domain.enums.ProductOrigin;
import afsj.efm.product.domain.enums.UnitOfMeasure;
import afsj.efm.product.domain.exceptions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductTest {

   String name = "semente";
   int stock = 100;
   int minimumStock = 10;
   UnitOfMeasure unit = UnitOfMeasure.UNIT;
   ProductOrigin origin = ProductOrigin.OWN_PRODUCTION;
   Category category = new Category("other");

   @Test
   @DisplayName("Should create a valid product")
   void createProduct() {
      Product product = new Product("semente", 100, minimumStock, UnitOfMeasure.UNIT, ProductOrigin.OWN_PRODUCTION, category);

      Assertions.assertEquals(name, product.getName());
      Assertions.assertEquals(stock, product.getStock());
      Assertions.assertEquals(minimumStock, product.getMinimumStock());
      Assertions.assertEquals(unit, product.getUnitOfMeasure());
      Assertions.assertEquals(origin, product.getProductOrigin());
   }

   @Test
   @DisplayName("Should throw error when name is null")
   void nameIsNull() {
      Assertions.assertThrows(InvalidProductException.class,
              () -> new Product(null, stock, minimumStock, unit, origin, category));
   }

   @Test
   @DisplayName("Should throw error when name is blank")
   void nameIsBlank() {
      Assertions.assertThrows(InvalidProductException.class,
              () -> new Product("   ", stock, minimumStock, unit, origin, category));
   }

   @Test
   @DisplayName("Should throw error when name is shorter than 3 characters")
   void nameIsShorter() {
      Assertions.assertThrows(InvalidProductException.class,
              () -> new Product("a", stock, minimumStock, unit, origin, category));
   }

   @Test
   @DisplayName("Should throw error when name is longer than 50 characters")
   void nameIsLonger() {
      Assertions.assertThrows(InvalidProductException.class,
              () -> new Product("a".repeat(51), stock, minimumStock, unit, origin, category));
   }

   @Test
   @DisplayName("Should throw error when unit of measure is null")
   void unitOfMeasureIsNull() {
      Assertions.assertThrows(InvalidProductException.class,
              () -> new Product(name, stock, minimumStock, null, origin, category));
   }

   @Test
   @DisplayName("Should set createdAt when product is created")
   void createdAtValid() {
      Product product = new Product(name, stock, minimumStock, unit, origin, category);

      Assertions.assertNotNull(product.getCreatedAt());
   }

   @Test
   @DisplayName("Should throw error when initial stock is negative")
   void initialStockIsNegative() {
      Assertions.assertThrows(InvalidProductException.class,
              () -> new Product(name, -10, minimumStock, unit, origin, category));
   }

   @Test
   @DisplayName("Should throw error when movement quantity is negative")
   void movementStockIsNegative() {
      Product product = new Product(name, stock, minimumStock, unit, origin, category);

      Assertions.assertThrows(InvalidProductException.class,
              () -> product.increaseStock(-1));

      Assertions.assertThrows(InvalidProductException.class,
              () -> product.decreaseStock(-1));

   }

   @Test
   @DisplayName("Should throw error when stock is insufficient")
   void stockIsInsufficient() {
      Product product = new Product(name, stock, minimumStock, unit, origin, category);
      Assertions.assertThrows(InvalidProductException.class,
              () -> product.decreaseStock(stock + 10));
   }

   @Test
   @DisplayName("Should increase stock and register IN movement")
   void increaseStock() {
      Product product = new Product(name, stock, minimumStock, unit, origin, category);

      product.increaseStock(10);

      Assertions.assertEquals(stock + 10, product.getStock());
      Assertions.assertEquals(1, product.getStockMovements().size());
   }

   @Test
   @DisplayName("Should throw error when movement quantity is zero")
   void movementQuantityIsZero() {
      Product product = new Product(name, stock, minimumStock, unit, origin, category);

      Assertions.assertThrows(InvalidProductException.class,
              () -> product.increaseStock(0));
   }

   @Test
   @DisplayName("Should not allow external modification of stock movements")
   void stockMovementShouldBeImmutable() {
      Product product = new Product(name, stock, minimumStock, unit, origin, category);

      product.increaseStock(10);

      Assertions.assertThrows(UnsupportedOperationException.class,
              () -> product.getStockMovements().add(null));
   }

   @Test
   @DisplayName("Should throw error when category is null")
   void throwErrorWhenCategoryIsNull() {
      Assertions.assertThrows(InvalidProductException.class,
              () -> new Product(name, stock, minimumStock, unit, origin, null));
   }

   @Test
   @DisplayName("Should allow minimum stock zero for own production")
   void allowZeroMinimumStockForOwnProduction() {
      Product product = new Product(name, stock, 0, unit, ProductOrigin.OWN_PRODUCTION, category);

      Assertions.assertEquals(0, product.getMinimumStock());
   }

   @Test
   @DisplayName("Should throw error when purchased product has zero minimum stock")
   void purchasedProductRequiresPositiveMinimumStock() {
      Assertions.assertThrows(InvalidProductException.class,
              () -> new Product(name, stock, 0, unit, ProductOrigin.PURCHASED, category));
   }
}
