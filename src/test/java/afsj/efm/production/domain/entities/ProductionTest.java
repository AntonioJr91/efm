package afsj.efm.production.domain.entities;

import afsj.efm.production.domain.exceptions.InvalidProductionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class ProductionTest {

   @Test
   @DisplayName("Should create production with valid data")
   void shouldCreateProduction() {
      var production = new Production(1L, 1L, 100, "observation");

      Assertions.assertEquals(1L, production.getAreaId());
      Assertions.assertEquals(1L, production.getProductId());
      Assertions.assertEquals(100, production.getQuantity());
      Assertions.assertEquals("observation", production.getObservation());
   }

   @Test
   @DisplayName("Should throw error when areaId is null, zero or negative")
   void shouldThrowErrorWhenAreaIdIsInvalid() {
      Assertions.assertThrows(InvalidProductionException.class,
              () -> new Production(null, 2L, 100, "observation"));

      Assertions.assertThrows(InvalidProductionException.class,
              () -> new Production(0L, 2L, 100, "observation"));

      Assertions.assertThrows(InvalidProductionException.class,
              () -> new Production(-1L, 2L, 100, "observation"));
   }

   @Test
   @DisplayName("Should throw error when productId is null, zero or negative")
   void shouldThrowErrorWhenProductIdIsInvalid() {
      Assertions.assertThrows(InvalidProductionException.class,
              () -> new Production(1L, null, 100, "observation"));

      Assertions.assertThrows(InvalidProductionException.class,
              () -> new Production(1L, 0L, 100, "observation"));

      Assertions.assertThrows(InvalidProductionException.class,
              () -> new Production(1L, -1L, 100, "observation"));
   }

   @Test
   @DisplayName("Should throw error when quantity zero or negative")
   void shouldThrowErrorWhenQuantityIsInvalid() {
      Assertions.assertThrows(InvalidProductionException.class,
              () -> new Production(1L, 2L, 0, "observation"));

      Assertions.assertThrows(InvalidProductionException.class,
              () -> new Production(1L, 2L, -1, "observation"));
   }

   @Test
   @DisplayName("Should trim observation")
   void shouldTrimObservation() {
      var production = new Production(1L, 1L, 100, "   observation   ");

      Assertions.assertEquals("observation", production.getObservation());
   }

   @Test
   @DisplayName("Should set observation to empty string when null")
   void shouldSetObservationToEmptyStringWhenNull() {
      var production = new Production(1L, 1L, 100, null);

      Assertions.assertTrue(production.getObservation().isEmpty());
   }

   @Test
   @DisplayName("Should set observation to empty string when blank")
   void shouldSetObservationToEmptyStringWhenBlank() {
      var production = new Production(1L, 1L, 100, "   ");

      Assertions.assertTrue(production.getObservation().isEmpty());
   }

   @Test
   @DisplayName("Should allow observation with max length")
   void shouldAllowObservationWithMaxLength() {
      String observation = "a".repeat(255);

      var production = new Production(1L, 1L, 100, observation);

      Assertions.assertEquals(observation, production.getObservation());
   }

   @Test
   @DisplayName("Should throw error when observation is too long")
   void shouldThrowErrorWhenObservationIsTooLong() {
      String observation = "a".repeat(256);

      Assertions.assertThrows(InvalidProductionException.class,
              () -> new Production(1L, 1L, 100, observation));
   }

   @Test
   @DisplayName("Should set createdAt to current date")
   void shouldSetCreatedAtToCurrentDate() {
      var production = new Production(1L, 1L, 100, "observation");

      Assertions.assertEquals(LocalDate.now(), production.getCreatedAt());
   }
}