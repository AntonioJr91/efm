package afsj.efm.employee.application.errors;

import afsj.efm.employee.domain.entities.Cpf;
import afsj.efm.employee.domain.entities.PhoneNumber;
import afsj.efm.shared.application.exceptions.ConflictException;

public final class EmployeeConflicts {

   public static ConflictException cpfAlreadyExists() {
      return ConflictException.of("Employee with CPF already exists");
   }

   public static ConflictException phoneNumberAlreadyExists() {
      return ConflictException.of("Employee with phone number already exists");
   }
}
