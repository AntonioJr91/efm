package afsj.efm.shared.application.exceptions;

public class BusinessException extends RuntimeException {
   public BusinessException(String message) {
      super(message);
   }

   public static BusinessException of(String message) {
      return new BusinessException(message);
   }
}
