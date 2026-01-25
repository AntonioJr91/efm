package afsj.efm.product.domain.exceptions;

public final class InvalidStockMovementQuantityException extends RuntimeException {
   public InvalidStockMovementQuantityException(String message) {
      super(message);
   }
}
