package afsj.efm.product.application.exceptions;

public final class ConflictException extends RuntimeException {
   private ConflictException(String message) {
      super(message);
   }

   public static ConflictException productName(String name) {
      return new ConflictException("Product with name '" + name + "' already exists");
   }
}
