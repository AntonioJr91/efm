package afsj.efm.service_order.application.farm_area.mappers;

import afsj.efm.service_order.application.farm_area.dtos.FarmAreaResponse;
import afsj.efm.service_order.domain.entities.FarmArea;

import java.util.List;

public final class FarmAreaMapper {

   public static FarmAreaResponse toDto(FarmArea farmArea) {
      return new FarmAreaResponse(
              farmArea.getId(),
              farmArea.getName()
      );
   }

   public static List<FarmAreaResponse> toDtoList(List<FarmArea> list) {
      return list.stream().map(FarmAreaMapper::toDto).toList();
   }
}
