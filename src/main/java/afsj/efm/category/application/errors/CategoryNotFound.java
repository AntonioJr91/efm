package afsj.efm.category.application.errors;

import afsj.efm.shared.application.exceptions.ResourceNotFoundException;

public final class CategoryNotFound {

   public static ResourceNotFoundException byId(Long id) {
      return ResourceNotFoundException.of("Category with id '" + id + "' not found");
   }

   public static ResourceNotFoundException byName(String name) {
      return ResourceNotFoundException.of("Category with name '" + name + "' not found");
   }
}
