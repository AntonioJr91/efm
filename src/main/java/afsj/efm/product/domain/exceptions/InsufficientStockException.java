package afsj.efm.product.domain.exceptions;

public final class InsufficientStockException extends RuntimeException {
   public InsufficientStockException(String message) {
      super(message);
   }
}
