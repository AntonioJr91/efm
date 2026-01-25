package afsj.efm.category.application.exceptions;

public final class ConflictException extends RuntimeException {
   private ConflictException(String message) {
      super(message);
   }

   public static ConflictException categoryName(String name) {
      return new ConflictException("Category with name '" + name + "' already exists");
   }
}
