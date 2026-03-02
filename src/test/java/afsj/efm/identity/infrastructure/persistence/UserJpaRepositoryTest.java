package afsj.efm.identity.infrastructure.persistence;

import afsj.efm.identity.domain.entities.Role;
import afsj.efm.identity.domain.entities.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class UserJpaRepositoryTest {

   @Autowired
   private UserJpaRepository userRepository;

   @Autowired
   private RoleJpaRepository roleRepository;

   @Test
   @DisplayName("Deve salvar usuário corretamente")
   void shouldSaveUser() {
      User user = new User("antonio", "123456");

      User saved = userRepository.save(user);

      assertThat(saved.getId()).isNotNull();
      assertThat(saved.getUsername()).isEqualTo("antonio");
   }

   @Test
   @DisplayName("Não deve permitir username duplicado")
   void shouldNotAllowDuplicateUsername() {
      userRepository.saveAndFlush(new User("antonio", "123"));

      assertThatThrownBy(() ->
              userRepository.saveAndFlush(new User("antonio", "456"))
      ).isInstanceOf(DataIntegrityViolationException.class);
   }

   @Test
   @DisplayName("existsByUsername deve retornar true quando usuário existir")
   void shouldReturnTrueWhenUsernameExists() {
      userRepository.save(new User("antonio", "123"));

      boolean exists = userRepository.existsByUsername("antonio");

      assertThat(exists).isTrue();
   }

   @Test
   @DisplayName("existsByUsername deve retornar false quando usuário não existir")
   void shouldReturnFalseWhenUsernameDoesNotExist() {
      boolean exists = userRepository.existsByUsername("unknown");

      assertThat(exists).isFalse();
   }

   @Test
   @DisplayName("Deve encontrar usuário por username")
   void shouldFindUserByUsername() {
      userRepository.save(new User("antonio", "123"));

      Optional<User> result = userRepository.findByUsername("antonio");

      assertThat(result).isPresent();
      assertThat(result.get().getUsername()).isEqualTo("antonio");
   }

   @Test
   @DisplayName("Deve retornar vazio quando username não existir")
   void shouldReturnEmptyWhenUsernameNotFound() {
      Optional<User> result = userRepository.findByUsername("unknown");

      assertThat(result).isEmpty();
   }

   @Test
   @DisplayName("findByUsernameWithRoles deve carregar roles associadas")
   void shouldFetchUserWithRoles() {

      Role admin = roleRepository.save(new Role("ADMIN"));
      Role manager = roleRepository.save(new Role("MANAGER"));

      User user = new User("antonio", "123");
      user.addRole(admin);
      user.addRole(manager);

      userRepository.saveAndFlush(user);

      Optional<User> result =
              userRepository.findByUsernameWithRoles("antonio");

      assertThat(result).isPresent();
      assertThat(result.get().getRoles())
              .extracting(Role::getName)
              .containsExactlyInAnyOrder("ADMIN", "MANAGER");
   }

   @Test
   @DisplayName("findByUsernameWithRoles deve retornar usuário mesmo sem roles")
   void shouldFetchUserWithoutRoles() {

      userRepository.saveAndFlush(new User("antonio", "123"));

      Optional<User> result =
              userRepository.findByUsernameWithRoles("antonio");

      assertThat(result).isPresent();
      assertThat(result.get().getRoles()).isEmpty();
   }
}