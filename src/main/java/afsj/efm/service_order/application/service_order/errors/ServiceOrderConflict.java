package afsj.efm.service_order.application.service_order.errors;

import afsj.efm.shared.application.exceptions.ConflictException;

public final class ServiceOrderConflict {

   public static ConflictException farmAreaAlreadyExists(String name) {
      return ConflictException.of("Service Order with name '" + name + "' already exists");
   }
}