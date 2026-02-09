package afsj.efm.service_order.domain.exceptions;

public class InvalidServiceOrderItemException extends RuntimeException {
   public InvalidServiceOrderItemException(String message) {
      super(message);
   }
}
