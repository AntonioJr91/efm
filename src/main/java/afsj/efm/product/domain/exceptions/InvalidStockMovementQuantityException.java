package afsj.efm.product.domain.exceptions;

public class InvalidStockMovementQuantityException extends RuntimeException {
   public InvalidStockMovementQuantityException(String message) {
      super(message);
   }
}
