package afsj.efm.service_order.domain.exceptions;

import afsj.efm.shared.application.exceptions.BusinessException;

public class InvalidServiceOrderException extends BusinessException {
   public InvalidServiceOrderException(String message) {
      super(message);
   }
}
