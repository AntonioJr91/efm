package afsj.efm.service_order.domain.entities;

import afsj.efm.service_order.domain.exceptions.InvalidFarmAreaException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FarmAreaTest {

   @Test
   @DisplayName("Should create a farm area")
   void createFarmArea() {
      FarmArea farmArea = new FarmArea("area 51");

      Assertions.assertEquals("area 51", farmArea.getName());
   }

   @Test
   @DisplayName("Should throw error when name is null or blank")
   void errorWhenNameIsNull() {
      Assertions.assertThrows(InvalidFarmAreaException.class,
              () -> new FarmArea(null));
      Assertions.assertThrows(InvalidFarmAreaException.class,
              () -> new FarmArea("   "));
   }

   @Test
   @DisplayName("Should throw error when name is less than 3 characters")
   void nameIsLessThen3Characters() {
      Assertions.assertThrows(InvalidFarmAreaException.class,
              () -> new FarmArea("a"));
   }

   @Test
   @DisplayName("Should throw error when name is greater than 50 characters")
   void nameIsGreaterThan50Characters() {
      Assertions.assertThrows(InvalidFarmAreaException.class,
              () -> new FarmArea("a".repeat(51)));
   }
}