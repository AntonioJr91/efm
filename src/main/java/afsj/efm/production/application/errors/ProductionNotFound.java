package afsj.efm.production.application.errors;

import afsj.efm.shared.application.exceptions.ResourceNotFoundException;

public final class ProductionNotFound {
   public static ResourceNotFoundException byId() {
      return ResourceNotFoundException.of("Production with id not found");
   }
}
