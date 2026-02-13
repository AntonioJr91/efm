package afsj.efm.service_order.application.farm_area.errors;

import afsj.efm.shared.application.exceptions.ResourceNotFoundException;

public final class FarmAreaNotFound {

   public static ResourceNotFoundException byId(Long id) {
      return ResourceNotFoundException.of("Farm Area with id '" + id + "' not found");
   }

   public static ResourceNotFoundException byName(String name) {
      return ResourceNotFoundException.of("Farm Area with name '" + name + "' not found");
   }
}