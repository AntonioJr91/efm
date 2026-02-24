package afsj.efm.identity.application.users.errors;

import afsj.efm.shared.application.exceptions.ConflictException;

public final class UserConflicts {
   public static ConflictException nameAlreadyExists(String name) {
      return ConflictException.of("User with name '" + name + "' already exists");
   }
}
