package afsj.efm.production.infraestructure.persistence;

import afsj.efm.production.domain.entities.Production;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

@DataJpaTest
class ProductionJpaRepositoryTest {

   @Autowired
   private ProductionJpaRepository repository;

   @Test
   @DisplayName("Should save production when it is valid")
   void shouldSaveProductionWhenItIsValid() {
      var production = new Production(1L, 1L, 100L, "observation");

      Production saved = repository.save(production);

      Assertions.assertNotNull(saved.getId());
      Assertions.assertEquals(1L, saved.getAreaId());
      Assertions.assertEquals(1L, saved.getProductId());
      Assertions.assertEquals(100L, saved.getQuantity());
      Assertions.assertEquals("observation", saved.getObservation());
      Assertions.assertEquals(LocalDate.now(), saved.getCreatedAt());
   }

   @Test
   @DisplayName("Should persist production and find it by id")
   void shouldPersistProductionAndFindItById() {
      var production = new Production(1L, 1L, 100L, "observation");

      Production saved = repository.save(production);
      Optional<Production> found = repository.findById(saved.getId());

      Assertions.assertTrue(found.isPresent());
      Assertions.assertEquals(saved.getId(), found.get().getId());
      Assertions.assertEquals(saved.getAreaId(), found.get().getAreaId());
      Assertions.assertEquals(saved.getProductId(), found.get().getProductId());
      Assertions.assertEquals(saved.getQuantity(), found.get().getQuantity());
      Assertions.assertEquals(saved.getObservation(), found.get().getObservation());
      Assertions.assertEquals(saved.getCreatedAt(), found.get().getCreatedAt());
   }

   @Test
   @DisplayName("Should save production with normalized observation")
   void shouldSaveProductionWithNormalizedObservation() {
      var production = new Production(1L, 1L, 100L, "   observation   ");

      Production saved = repository.save(production);

      Assertions.assertNotNull(saved.getId());
      Assertions.assertEquals("observation", saved.getObservation());
   }
}