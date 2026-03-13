package afsj.efm.employee.domain.exceptions;

import afsj.efm.shared.application.exceptions.BusinessException;

public class InvalidCpfException extends BusinessException {
   public InvalidCpfException(String message) {
      super(message);
   }
}
