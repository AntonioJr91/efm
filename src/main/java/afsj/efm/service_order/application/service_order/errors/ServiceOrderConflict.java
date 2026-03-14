package afsj.efm.service_order.application.service_order.errors;

import afsj.efm.shared.application.exceptions.ConflictException;

public final class ServiceOrderConflict {

   public static ConflictException serviceOrderAlreadyExists() {
      return ConflictException.of("Service Order with name already exists");
   }
   public static ConflictException productAlreadyAdded() {
      return ConflictException.of("The product has already been added to this service order.");
   }
}