package afsj.efm.employee.domain.entities;

import afsj.efm.employee.domain.exceptions.InvalidCpfException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record Cpf(
        @Column(name = "cpf", length = 11, nullable = false, updatable = false, unique = true)
        String value) {

   public Cpf {
      value = validateAndNormalizeCpf(value);
   }

   private String validateAndNormalizeCpf(String cpf) {
      if (cpf == null) throw new InvalidCpfException("CPF_IS_REQUIRED");

      String normalized = cpf.replaceAll("\\D", "");

      if (!isValid(normalized)) {
         throw new InvalidCpfException("CPF_IS_INVALID");
      }

      return normalized;
   }

   private boolean isValid(String cpf) {
      if (cpf.length() != 11) return false;
      if (cpf.chars().distinct().count() == 1) return false;

      int sum = 0;
      for (int i = 0; i < 9; i++)
         sum += (cpf.charAt(i) - '0') * (10 - i);

      int firstDigit = 11 - (sum % 11);
      firstDigit = firstDigit > 9 ? 0 : firstDigit;

      sum = 0;
      for (int i = 0; i < 10; i++)
         sum += (cpf.charAt(i) - '0') * (11 - i);

      int secondDigit = 11 - (sum % 11);
      secondDigit = secondDigit > 9 ? 0 : secondDigit;

      return firstDigit == (cpf.charAt(9) - '0')
              && secondDigit == (cpf.charAt(10) - '0');

   }
}
