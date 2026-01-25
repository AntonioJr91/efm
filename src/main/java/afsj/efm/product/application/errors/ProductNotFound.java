package afsj.efm.product.application.errors;

import afsj.efm.shared.application.exceptions.ResourceNotFoundException;

public final class ProductNotFound {
   public static ResourceNotFoundException byId(Long id) {
      return ResourceNotFoundException.of("Product with id '" + id + "' not found");
   }

   public static ResourceNotFoundException byName(String name) {
      return ResourceNotFoundException.of("Product with name '" + name + "' not found");
   }
}
