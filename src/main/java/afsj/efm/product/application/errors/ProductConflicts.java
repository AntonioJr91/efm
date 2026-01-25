package afsj.efm.product.application.errors;

import afsj.efm.shared.application.exceptions.ConflictException;

public final class ProductConflicts {
   public static ConflictException nameAlreadyExists(String name) {
      return ConflictException.of("Product with name '" + name + "' already exists");
   }
}
