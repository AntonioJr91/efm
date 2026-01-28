package afsj.efm.employee.domain.exceptions;

public class InvalidTerminationDateException extends RuntimeException {
   public InvalidTerminationDateException(String message) {
      super(message);
   }
}
