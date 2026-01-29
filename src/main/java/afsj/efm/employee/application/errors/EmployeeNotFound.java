package afsj.efm.employee.application.errors;

import afsj.efm.shared.application.exceptions.ResourceNotFoundException;

public final class EmployeeNotFound {

   public static ResourceNotFoundException byId(Long id) {
      return ResourceNotFoundException.of("Employee with id '" + id + "' not found");
   }

   public static ResourceNotFoundException byName(String name) {
      return ResourceNotFoundException.of("Employee with name '" + name + "' not found");
   }
}