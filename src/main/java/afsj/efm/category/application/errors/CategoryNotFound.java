package afsj.efm.category.application.errors;

import afsj.efm.shared.application.exceptions.ResourceNotFoundException;

public final class CategoryNotFound {

   public static ResourceNotFoundException byId() {
      return ResourceNotFoundException.of("Category with id not found");
   }
}
