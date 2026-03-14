package afsj.efm.service_order.application.service_order.errors;

import afsj.efm.shared.application.exceptions.ConflictException;

public final class ServiceOrderConflict {

   public static ConflictException serviceOrderAlreadyExists() {
      return ConflictException.of("Service Order with name already exists");
   }
}