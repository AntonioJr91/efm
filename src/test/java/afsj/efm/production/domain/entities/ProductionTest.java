package afsj.efm.production.domain.entities;

import afsj.efm.production.domain.exceptions.InvalidProductionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductionTest {

   @Test
   @DisplayName("Should create production with valid data")
   void shouldCreateProduction() {
      var production = new Production(1L, 1L, 100L, "observation");
      assert production.getAreaId() == 1L;
      assert production.getProductId() == 1L;
      assert production.getQuantity() == 100L;
      assert production.getObservation().equals("observation");
   }

   @Test
   @DisplayName("Should throw error when areaId is invalid or null")
   void shouldThrowErrorWhenAreaIdIsInvalidOrNull() {
      Assertions.assertThrows(InvalidProductionException.class, () -> new Production(null, 2L, 100L, "observation"));
      Assertions.assertThrows(InvalidProductionException.class, () -> new Production(-1L, 2L, 100L, "observation"));
   }

   @Test
   @DisplayName("Should throw error when productId is invalid or null")
   void shouldThrowErrorWhenProductIdIsInvalidOrNull() {
      Assertions.assertThrows(InvalidProductionException.class, () -> new Production(1L, null, 100L, "observation"));
      Assertions.assertThrows(InvalidProductionException.class, () -> new Production(1L, -1L, 100L, "observation"));
   }

   @Test
   @DisplayName("Should throw error when quantity is invalid or null")
   void shouldThrowErrorWhenQuantityIsInvalidOrNull() {
      Assertions.assertThrows(InvalidProductionException.class, () -> new Production(1L, 2L, null, "observation"));
      Assertions.assertThrows(InvalidProductionException.class, () -> new Production(1L, 2L, -1L, "observation"));
   }

   @Test
   @DisplayName("Should trim observation and set to empty string if null")
   void shouldTrimObservationAndSetToEmptyStringIfNull() {
      var production1 = new Production(1L, 1L, 100L, "   observation   ");
      assert production1.getObservation().equals("observation");
   }

   @Test
   @DisplayName("Should set observation to empty string if null")
   void shouldSetObservationToEmptyStringIfNull() {
      var production2 = new Production(1L, 1L, 100L, null);
      assert production2.getObservation().isEmpty();
   }

   @Test
   @DisplayName("Should set createdAt to current date")
   void shouldSetCreatedAtToCurrentDate() {
      var production = new Production(1L, 1L, 100L, "observation");
      assert production.getCreatedAt().equals(java.time.LocalDate.now());
   }
}