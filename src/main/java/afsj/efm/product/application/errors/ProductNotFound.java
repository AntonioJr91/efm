package afsj.efm.product.application.errors;

import afsj.efm.shared.application.exceptions.ResourceNotFoundException;

public final class ProductNotFound {
   public static ResourceNotFoundException byId() {
      return ResourceNotFoundException.of("Product with id not found");
   }
}
