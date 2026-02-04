package afsj.efm.service_order.application.service_order.mappers;

import afsj.efm.service_order.application.service_order.dtos.ServiceOrderResponse;
import afsj.efm.service_order.domain.entities.ServiceOrder;

import java.util.List;

public final class ServiceOrderMapper {

   public static ServiceOrderResponse toDto(ServiceOrder serviceOrder) {
      return new ServiceOrderResponse(
              serviceOrder.getId(),
              serviceOrder.getEmployee().getFirstName(),
              serviceOrder.getFarmArea().getName(),
              serviceOrder.getServiceType().name(),
              serviceOrder.getStatusOrder().name(),
              serviceOrder.getCreatedAt()
      );
   }

   public static List<ServiceOrderResponse> toDtoList(List<ServiceOrder> list) {
      return list.stream().map(ServiceOrderMapper::toDto).toList();
   }
}
