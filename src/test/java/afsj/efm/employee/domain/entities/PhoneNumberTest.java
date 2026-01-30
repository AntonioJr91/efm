package afsj.efm.employee.domain.entities;

import afsj.efm.employee.domain.exceptions.InvalidPhoneNumberException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PhoneNumberTest {

   @Test
   @DisplayName("Should create phone number when is valid")
   void createWhenIsValid() {
      PhoneNumber phoneNumber = new PhoneNumber("27999282715");

      Assertions.assertEquals("27999282715", phoneNumber.value());
   }

   @Test
   @DisplayName("Should throw error when phone number length is different of 11 characters")
   void throwErrorWhenLengthIsDifferent() {
      Assertions.assertThrows(InvalidPhoneNumberException.class,
              () -> new PhoneNumber("1"));

      Assertions.assertThrows(InvalidPhoneNumberException.class,
              () -> new PhoneNumber("123456789012"));
   }

   @Test
   @DisplayName("Should normalize phone number removing whitespaces")
   void normalizeWhenHasWhitespaces() {
      PhoneNumber phoneNumber = new PhoneNumber(" 27999282715 ");

      Assertions.assertEquals("27999282715", phoneNumber.value());
   }

   @Test
   @DisplayName("Should throw error when phone number has invalid format")
   void throwErrorWhenFormatIsInvalid() {
      Assertions.assertThrows(InvalidPhoneNumberException.class,
              () -> new PhoneNumber("27111111111"));
   }

   @Test
   @DisplayName("Should throw error when phone number does not start with digit nine")
   void throwErrorWhenDoesNotStartWithNine() {
      Assertions.assertThrows(InvalidPhoneNumberException.class,
              () -> new PhoneNumber("27899282715"));
   }

   @Test
   @DisplayName("Should throw error when phone number has invalid DDD")
   void throwErrorWhenDDDIsInvalid() {
      Assertions.assertThrows(InvalidPhoneNumberException.class,
              () -> new PhoneNumber("00999282715"));
   }

   @Test
   @DisplayName("Should create phone number when is null")
   void createWhenIsNull() {
      PhoneNumber phoneNumber = new PhoneNumber(null);

      Assertions.assertNull(phoneNumber.value());
   }
}