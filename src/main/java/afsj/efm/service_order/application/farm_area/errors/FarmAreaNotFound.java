package afsj.efm.service_order.application.farm_area.errors;

import afsj.efm.shared.application.exceptions.ResourceNotFoundException;

public final class FarmAreaNotFound {

   public static ResourceNotFoundException byId() {
      return ResourceNotFoundException.of("Farm Area with id not found");
   }
}