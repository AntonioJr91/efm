package afsj.efm.service_order.infrastructure.persistence;

import afsj.efm.service_order.domain.entities.FarmArea;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class FarmAreaJpaRepositoryTest {

   FarmArea farmArea;
   FarmArea saved;

   @Autowired
   private FarmAreaJpaRepository repository;

   @BeforeEach
   void setUp() {
      farmArea = new FarmArea("area 51");
      saved = repository.saveAndFlush(farmArea);
   }

   @Test
   @DisplayName("Should save when it is valid")
   void saveFarmArea() {
      Assertions.assertNotNull(saved.getId());
      Assertions.assertEquals("area 51", saved.getName());
   }

   @Test
   @DisplayName("Should return a farm area by id")
   void findFarmAreaById() {
      var exists = repository.findById(saved.getId());

      Assertions.assertTrue(exists.isPresent());
      Assertions.assertEquals(saved.getId(), exists.get().getId());
   }

   @Test
   @DisplayName("Should return a farm area by name")
   void findFarmAreaByName() {
      var exists = repository.findByName(saved.getName());

      Assertions.assertTrue(exists.isPresent());
      Assertions.assertEquals(saved.getName(), exists.get().getName());
   }

   @Test
   @DisplayName("Should return empty when name does not exist")
   void returnEmptyWhenNotExist() {
      var exists = repository.findByName("unknown");

      Assertions.assertTrue(exists.isEmpty());
   }

   @Test
   @DisplayName("Should delete farm area")
   void deleteFarmArea() {
      repository.deleteById(saved.getId());
      repository.flush();

      var result = repository.findById(saved.getId());

      Assertions.assertTrue(result.isEmpty());
   }

   @Test
   @DisplayName("Should count farm areas")
   void countFarmAreas() {
      long count = repository.count();

      Assertions.assertEquals(1, count);
   }

   @Test
   @DisplayName("Should throw error when farm area name is duplicated")
   void errorWhenFarmAreaNameIsDuplicated() {
      Assertions.assertThrows(
              DataIntegrityViolationException.class,
              () -> repository.saveAndFlush(new FarmArea("area 51"))
      );
   }
}
