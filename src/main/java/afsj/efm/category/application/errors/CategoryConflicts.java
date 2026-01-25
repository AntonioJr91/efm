package afsj.efm.category.application.errors;

import afsj.efm.shared.application.exceptions.ConflictException;

public final class CategoryConflicts {
   public static ConflictException nameAlreadyExists(String name) {
      return ConflictException.of("Category with name '" + name + "' already exists");
   }
}
