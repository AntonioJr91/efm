package afsj.efm.employee.domain.entities;

import afsj.efm.employee.domain.exceptions.InvalidPhoneNumberException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record PhoneNumber(
        @Column(name = "phone_number", length = 11, nullable = true, updatable = true, unique = true)
        String value) {

   public PhoneNumber {
      value = value.trim();

      if (!value.matches("^[1-9]{2}9\\d{8}$")) {
         throw new InvalidPhoneNumberException("INVALID_FORMAT_PHONE_NUMBER");
      }
   }
}
