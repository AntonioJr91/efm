package afsj.efm.identity.infrastructure.persistence;

import afsj.efm.identity.domain.entities.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class RoleJpaRepositoryTest {

   @Autowired
   private RoleJpaRepository repository;

   @Test
   @DisplayName("Deve salvar role corretamente")
   void shouldSaveRole() {
      Role role = new Role("ADMIN");

      Role saved = repository.save(role);

      assertThat(saved.getId()).isNotNull();
      assertThat(saved.getName()).isEqualTo("ADMIN");
   }

   @Test
   @DisplayName("Deve retornar true quando existsByName encontrar role")
   void shouldReturnTrueWhenRoleExistsByName() {
      repository.save(new Role("ADMIN"));

      boolean exists = repository.existsByName("ADMIN");

      assertThat(exists).isTrue();
   }

   @Test
   @DisplayName("Deve retornar false quando role não existir")
   void shouldReturnFalseWhenRoleDoesNotExist() {
      boolean exists = repository.existsByName("MANAGER");

      assertThat(exists).isFalse();
   }

   @Test
   @DisplayName("Deve encontrar role pelo nome")
   void shouldFindRoleByName() {
      repository.save(new Role("ADMIN"));

      Optional<Role> role = repository.findByName("ADMIN");

      assertThat(role).isPresent();
      assertThat(role.get().getName()).isEqualTo("ADMIN");
   }

   @Test
   @DisplayName("Deve retornar Optional vazio quando não encontrar role")
   void shouldReturnEmptyWhenRoleNotFound() {
      Optional<Role> role = repository.findByName("UNKNOWN");

      assertThat(role).isEmpty();
   }

   @Test
   @DisplayName("Não deve permitir salvar duas roles com mesmo nome")
   void shouldNotAllowDuplicateRoleNames() {
      repository.save(new Role("ADMIN"));

      assertThatThrownBy(() -> repository.saveAndFlush(new Role("ADMIN")))
              .isInstanceOf(Exception.class);
   }
}