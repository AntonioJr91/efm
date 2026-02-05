package afsj.efm.service_order.application.service_order.mappers;

import afsj.efm.service_order.application.service_order.dtos.ServiceOrderDetailResponse;
import afsj.efm.service_order.application.service_order.dtos.ServiceOrderResponse;
import afsj.efm.service_order.domain.entities.ServiceOrder;

import java.util.List;

public final class ServiceOrderMapper {

   public static ServiceOrderResponse toDto(ServiceOrder serviceOrder) {
      return new ServiceOrderResponse(
              serviceOrder.getId(),
              serviceOrder.getEmployee().getFirstName(),
              serviceOrder.getFarmArea().getName(),
              serviceOrder.getServiceType().serviceTypeName(),
              serviceOrder.getStatusOrder().name(),
              serviceOrder.getCreatedAt()
      );
   }

   public static List<ServiceOrderResponse> toDtoList(List<ServiceOrder> list) {
      return list.stream().map(ServiceOrderMapper::toDto).toList();
   }

   public static ServiceOrderDetailResponse toDtoDetail(ServiceOrder serviceOrder) {
      return new ServiceOrderDetailResponse(
              serviceOrder.getId(),
              serviceOrder.getEmployee().getFirstName(),
              serviceOrder.getEmployee().getJobRole().name(),
              serviceOrder.getFarmArea().getName(),
              serviceOrder.getServiceType().serviceTypeName(),
              serviceOrder.getServiceType().serviceTypeDescription(),
              serviceOrder.getServiceType().serviceTypeCategory().name(),
              serviceOrder.getStatusOrder().name(),
              serviceOrder.getCreatedAt(),
              serviceOrder.getFinishedAt(),
              ServiceOrderItemMapper.toDoList(serviceOrder.getItems())
      );
   }

   public static List<ServiceOrderDetailResponse> toDtoListDetails(List<ServiceOrder> list) {
      return list.stream().map(ServiceOrderMapper::toDtoDetail).toList();
   }
}
