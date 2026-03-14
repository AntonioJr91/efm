package afsj.efm.employee.application.errors;

import afsj.efm.shared.application.exceptions.ResourceNotFoundException;

public final class EmployeeNotFound {

   public static ResourceNotFoundException byId() {
      return ResourceNotFoundException.of("Employee with id not found");
   }

   public static ResourceNotFoundException byName() {
      return ResourceNotFoundException.of("Employee with not found");
   }
}