package afsj.efm.service_order.domain.exceptions;

public class InvalidServiceOrderException extends RuntimeException {
   public InvalidServiceOrderException(String message) {
      super(message);
   }
}
