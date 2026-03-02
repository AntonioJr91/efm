package afsj.efm.identity.domain.entities;

import afsj.efm.identity.domain.exceptions.InvalidRoleException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class RoleTest {

   @Test
   @DisplayName("Deve criar role válida e normalizar nome")
   void shouldCreateRoleWithNormalizedName() {
      Role role = new Role("  admin  ");

      assertThat(role.getName()).isEqualTo("ADMIN");
   }

   @Test
   @DisplayName("Deve lançar exceção quando nome for null")
   void shouldThrowExceptionWhenNameIsNull() {
      assertThatThrownBy(() -> new Role(null))
              .isInstanceOf(InvalidRoleException.class)
              .hasMessage("ROLE_NAME_IS_REQUIRED");
   }

   @Test
   @DisplayName("Deve lançar exceção quando nome for blank")
   void shouldThrowExceptionWhenNameIsBlank() {
      assertThatThrownBy(() -> new Role("   "))
              .isInstanceOf(InvalidRoleException.class)
              .hasMessage("ROLE_NAME_IS_REQUIRED");
   }

   @Test
   @DisplayName("Deve lançar exceção quando nome for menor que 3 caracteres")
   void shouldThrowExceptionWhenNameIsTooShort() {
      assertThatThrownBy(() -> new Role("ab"))
              .isInstanceOf(InvalidRoleException.class)
              .hasMessage("ROLE_NAME_TOO_SHORT");
   }

   @Test
   @DisplayName("Deve lançar exceção quando nome for maior que 50 caracteres")
   void shouldThrowExceptionWhenNameIsTooLong() {
      String longName = "a".repeat(51);

      assertThatThrownBy(() -> new Role(longName))
              .isInstanceOf(InvalidRoleException.class)
              .hasMessage("ROLE_NAME_TOO_LONG");
   }

   @Test
   @DisplayName("Nome já em uppercase deve permanecer correto")
   void shouldKeepUppercaseName() {
      Role role = new Role("MANAGER");

      assertThat(role.getName()).isEqualTo("MANAGER");
   }
}