package afsj.efm.product.application.errors;

import afsj.efm.shared.application.exceptions.ConflictException;

public final class ProductConflicts {
   public static ConflictException nameAlreadyExists() {
      return ConflictException.of("Product with name already exists");
   }
}
