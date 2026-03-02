package afsj.efm.identity.domain.entities;

import afsj.efm.identity.domain.exceptions.InvalidRoleException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class UserTest {

   @Test
   @DisplayName("Deve criar usuário com username válido")
   void shouldCreateUserWithValidUsername() {
      User user = new User("antonio", "123456");

      assertThat(user.getUsername()).isEqualTo("antonio");
      assertThat(user.getPassword()).isEqualTo("123456");
      assertThat(user.getRoles()).isEmpty();
   }

   @Test
   @DisplayName("Deve lançar exceção quando username for null")
   void shouldThrowExceptionWhenUsernameIsNull() {
      assertThatThrownBy(() -> new User(null, "123"))
              .isInstanceOf(InvalidRoleException.class)
              .hasMessage("USER_NAME_IS_REQUIRED");
   }

   @Test
   @DisplayName("Deve lançar exceção quando username for blank")
   void shouldThrowExceptionWhenUsernameIsBlank() {
      assertThatThrownBy(() -> new User("   ", "123"))
              .isInstanceOf(InvalidRoleException.class)
              .hasMessage("USER_NAME_IS_REQUIRED");
   }

   @Test
   @DisplayName("Deve lançar exceção quando username for menor que 3 caracteres")
   void shouldThrowExceptionWhenUsernameTooShort() {
      assertThatThrownBy(() -> new User("ab", "123"))
              .isInstanceOf(InvalidRoleException.class)
              .hasMessage("USER_NAME_TOO_SHORT");
   }

   @Test
   @DisplayName("Deve lançar exceção quando username for maior que 50 caracteres")
   void shouldThrowExceptionWhenUsernameTooLong() {
      String longName = "a".repeat(51);

      assertThatThrownBy(() -> new User(longName, "123"))
              .isInstanceOf(InvalidRoleException.class)
              .hasMessage("USER_NAME_TOO_LONG");
   }

   @Test
   @DisplayName("Deve adicionar role ao usuário")
   void shouldAddRole() {
      User user = new User("antonio", "123");
      Role role = new Role("ADMIN");

      user.addRole(role);

      List<Role> roles = user.getRoles();

      assertThat(roles).hasSize(1);
      assertThat(roles).contains(role);
   }

   @Test
   @DisplayName("Não deve adicionar role duplicada")
   void shouldNotAddDuplicateRole() {
      User user = new User("antonio", "123");
      Role role = new Role("ADMIN");

      user.addRole(role);
      user.addRole(role);

      assertThat(user.getRoles()).hasSize(1);
   }

   @Test
   @DisplayName("Deve permitir adicionar múltiplas roles diferentes")
   void shouldAddMultipleDifferentRoles() {
      User user = new User("antonio", "123");

      Role admin = new Role("ADMIN");
      Role manager = new Role("MANAGER");

      user.addRole(admin);
      user.addRole(manager);

      assertThat(user.getRoles()).hasSize(2);
      assertThat(user.getRoles()).containsExactlyInAnyOrder(admin, manager);
   }
}