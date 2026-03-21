package afsj.efm.product.domain.exceptions;

public final class InvalidStockMovementException extends RuntimeException {
   public InvalidStockMovementException(String message) {
      super(message);
   }
}
