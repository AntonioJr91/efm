package afsj.efm.production.application.mappers;

import afsj.efm.production.application.dtos.ProductionRequest;
import afsj.efm.production.application.dtos.ProductionResponse;
import afsj.efm.production.domain.entities.Production;

import java.util.List;

public final class ProductionMapper {
   public static ProductionResponse toDto(Production production) {
      return new ProductionResponse(
              production.getId(),
              production.getAreaId(),
              production.getProductId(),
              production.getQuantity(),
              production.getObservation(),
              production.getCreatedAt()
      );
   }

   public static List<ProductionResponse> toDto(List<Production> productions) {
      return productions.stream().map(ProductionMapper::toDto).toList();
   }

   public static Production toEntity(ProductionRequest request) {
      return new Production(
              request.areaId(),
              request.productId(),
              request.quantity(),
              request.observation()
      );
   }
}
