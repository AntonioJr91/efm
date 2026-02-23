package afsj.efm.identity.application.errors;

import afsj.efm.shared.application.exceptions.ConflictException;

public final class RoleConflicts {
   public static ConflictException nameAlreadyExists(String name) {
      return ConflictException.of("Role with name '" + name + "' already exists");
   }
}
