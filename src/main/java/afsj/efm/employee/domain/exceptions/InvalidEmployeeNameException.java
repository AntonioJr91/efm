package afsj.efm.employee.domain.exceptions;

public class InvalidEmployeeNameException extends RuntimeException {
   public InvalidEmployeeNameException(String message) {
      super(message);
   }
}
