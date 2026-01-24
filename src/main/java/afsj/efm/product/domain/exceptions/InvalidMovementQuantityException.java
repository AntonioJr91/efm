package afsj.efm.product.domain.exceptions;

public class InvalidMovementQuantityException extends RuntimeException {
   public InvalidMovementQuantityException(String message) {
      super(message);
   }
}
