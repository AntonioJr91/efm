package afsj.efm.service_order.application.service_order.errors;

import afsj.efm.shared.application.exceptions.ResourceNotFoundException;

public final class ServiceOrderNotFound {

   public static ResourceNotFoundException byId() {
      return ResourceNotFoundException.of("Service Order with id not found");
   }
}