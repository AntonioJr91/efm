package afsj.efm.service_order.application.service_order.errors;

import afsj.efm.shared.application.exceptions.ResourceNotFoundException;

public final class ServiceOrderNotFound {

   public static ResourceNotFoundException byId(Long id) {
      return ResourceNotFoundException.of("Service Order with id '" + id + "' not found");
   }

   public static ResourceNotFoundException byName(String name) {
      return ResourceNotFoundException.of("Service Order with name '" + name + "' not found");
   }
}