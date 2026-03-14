package afsj.efm.category.application.errors;

import afsj.efm.shared.application.exceptions.ConflictException;

public final class CategoryConflicts {

   private CategoryConflicts() {
   }

   public static ConflictException nameAlreadyExists() {
      return ConflictException.of("Category with name already exists");
   }
}
