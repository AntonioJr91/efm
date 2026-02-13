package afsj.efm.service_order.application.service_order.mappers;

import afsj.efm.service_order.application.service_order.dtos.ServiceOrderItemResponse;
import afsj.efm.service_order.domain.entities.ServiceOrderItem;

import java.util.List;

public final class ServiceOrderItemMapper {

   public static ServiceOrderItemResponse toDto(ServiceOrderItem item) {
      return new ServiceOrderItemResponse(
              item.getId(),
              item.getProduct().getId(),
              item.getProduct().getName(),
              item.getQuantity()
      );
   }

   public static List<ServiceOrderItemResponse> toDoList(List<ServiceOrderItem> items) {
      return items.stream().map(ServiceOrderItemMapper::toDto).toList();
   }
}
