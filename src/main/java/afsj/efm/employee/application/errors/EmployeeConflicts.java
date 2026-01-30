package afsj.efm.employee.application.errors;

import afsj.efm.employee.domain.entities.Cpf;
import afsj.efm.employee.domain.entities.PhoneNumber;
import afsj.efm.shared.application.exceptions.ConflictException;

public final class EmployeeConflicts {

   public static ConflictException cpfAlreadyExists(Cpf cpf) {
      return ConflictException.of("Employee with CPF '"+ cpf.value() +"' already exists");
   }

   public static ConflictException phoneNumberAlreadyExists(PhoneNumber phoneNumber) {
      return ConflictException.of("Employee with phone number '"+ phoneNumber.value() +"' already exists");
   }
}
