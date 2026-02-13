package afsj.efm.employee.domain.exceptions;

public class InvalidEmployeeException extends RuntimeException {
   public InvalidEmployeeException(String message) {
      super(message);
   }
}
