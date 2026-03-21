package afsj.efm.product.application.mappers;

import afsj.efm.product.application.dtos.StockUpdateResponse;
import afsj.efm.product.domain.entities.Product;

public final class StockMapper {
   public static StockUpdateResponse toResponse(Product product) {
      return new StockUpdateResponse(product.getId(), product.getName(), product.getStock());
   }
}
