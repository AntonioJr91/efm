package afsj.efm.identity.application.users.errors;

import afsj.efm.shared.application.exceptions.ResourceNotFoundException;

public final class UserNotFound {

   public static ResourceNotFoundException byId(Long id) {
      return ResourceNotFoundException.of("User with id '" + id + "' not found");
   }

   public static ResourceNotFoundException byName(String name) {
      return ResourceNotFoundException.of("User with name '" + name + "' not found");
   }
}
