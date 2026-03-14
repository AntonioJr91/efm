package afsj.efm.service_order.application.farm_area.errors;

import afsj.efm.shared.application.exceptions.ConflictException;

public final class FarmAreaConflict {

   public static ConflictException farmAreaAlreadyExists() {
      return ConflictException.of("Farm Area with name already exists");
   }
}