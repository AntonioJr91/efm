package afsj.efm.product.application.mappers;

import afsj.efm.category.domain.entities.Category;
import afsj.efm.product.application.dtos.ProductRequest;
import afsj.efm.product.application.dtos.ProductResponse;
import afsj.efm.product.domain.entities.Product;

import java.util.List;

public final class ProductMapper {

   public static ProductResponse toDto(Product product) {
      return new ProductResponse(
              product.getId(),
              product.getName(),
              product.getStock(),
              product.getMinimumStock(),
              product.getUnitOfMeasure(),
              product.getProductOrigin(),
              product.getCreatedAt(),
              product.getCategory().getId()
      );
   }

   public static List<ProductResponse> toDtoList(List<Product> list) {
      return list.stream().map(ProductMapper::toDto).toList();
   }

   public static Product toEntity(ProductRequest request, Category category) {
      return new Product(
              request.name(),
              request.stock(),
              request.minimumStock(),
              request.unitOfMeasure(),
              request.productOrigin(),
              category
      );
   }
}
