package afsj.efm.employee.domain.exceptions;

public class InvalidPhoneNumberException extends RuntimeException {
   public InvalidPhoneNumberException(String message) {
      super(message);
   }
}
