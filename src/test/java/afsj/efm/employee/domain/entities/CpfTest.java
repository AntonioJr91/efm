package afsj.efm.employee.domain.entities;

import afsj.efm.employee.domain.exceptions.InvalidCpfException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CpfTest {

   @Test
   @DisplayName("Should create when is a valid cpf")
   void validCpf() {
      String validCpf = "39053344705";

      Cpf cpf = new Cpf(validCpf);

      assertEquals(validCpf, cpf.value());
   }

   @Test
   @DisplayName("Should throw error when cpf length is different of 11 characters")
   void invalidCpf() {
      assertThrows(InvalidCpfException.class,
              () -> new Cpf("1"));

      assertThrows(InvalidCpfException.class,
              () -> new Cpf("123456789012"));
   }

   @Test
   @DisplayName("Should throw error when cpf is null")
   void isNull() {
      assertThrows(InvalidCpfException.class,
              () -> new Cpf(null));
   }

   @Test
   @DisplayName("Should normalize cpf with mask and whitespaces")
   void normalizeCpf() {
      Cpf cpf = new Cpf(" 390.533.447-05 ");
      assertEquals("39053344705", cpf.value());
   }

   @Test
   @DisplayName("Should throw error when cpf has repeated numbers")
   void repeatedNumbers() {
      assertThrows(InvalidCpfException.class,
              () -> new Cpf("11111111111"));
   }
}