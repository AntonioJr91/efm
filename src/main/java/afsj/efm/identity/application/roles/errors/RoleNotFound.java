package afsj.efm.identity.application.roles.errors;

import afsj.efm.shared.application.exceptions.ResourceNotFoundException;

public final class RoleNotFound {

   public static ResourceNotFoundException byId(Long id) {
      return ResourceNotFoundException.of("Role with id '" + id + "' not found");
   }

   public static ResourceNotFoundException byName(String name) {
      return ResourceNotFoundException.of("Role with name '" + name + "' not found");
   }
}
