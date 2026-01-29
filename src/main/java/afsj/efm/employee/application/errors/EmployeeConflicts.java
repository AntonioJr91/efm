package afsj.efm.employee.application.errors;

import afsj.efm.employee.domain.entities.Cpf;
import afsj.efm.shared.application.exceptions.ConflictException;

public final class EmployeeConflicts {

   public static ConflictException cpfAlreadyExists(Cpf cpf) {
      return ConflictException.of("Employee with CPF '" + cpf + "' already exists");
   }
}
