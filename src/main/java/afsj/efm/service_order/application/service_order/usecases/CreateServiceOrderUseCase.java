package afsj.efm.service_order.application.service_order.usecases;

import afsj.efm.employee.infrastructure.persistence.EmployeeJpaRepository;
import afsj.efm.service_order.application.service_order.dtos.ServiceOrderRequest;
import afsj.efm.service_order.application.service_order.dtos.ServiceOrderResponse;
import afsj.efm.service_order.application.service_order.mappers.ServiceOrderMapper;
import afsj.efm.service_order.domain.entities.ServiceOrder;
import afsj.efm.service_order.domain.entities.ServiceType;
import afsj.efm.service_order.infrastructure.persistence.FarmAreaJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateServiceOrderUseCase {

   private final EmployeeJpaRepository employeeJpaRepository;
   private final FarmAreaJpaRepository farmAreaJpaRepository;

   public CreateServiceOrderUseCase(
           EmployeeJpaRepository employeeJpaRepository,
           FarmAreaJpaRepository farmAreaJpaRepository
   ) {
      this.employeeJpaRepository = employeeJpaRepository;
      this.farmAreaJpaRepository = farmAreaJpaRepository;
   }

   @Transactional
   public ServiceOrderResponse execute(ServiceOrderRequest request) {
      var employee = employeeJpaRepository.findByFirstName(request.employeeName())
              .orElseThrow(() -> new IllegalArgumentException());

      var farmArea = farmAreaJpaRepository.findByName(request.farmAreaName())
              .orElseThrow(() -> new IllegalArgumentException());

      var serviceType = new ServiceType(
              request.serviceTypeName(),
              request.serviceTypeDescription(),
              request.serviceTypeCategory()
      );

      var serviceOrder = new ServiceOrder(employee, farmArea, serviceType);

      return ServiceOrderMapper.toDto(serviceOrder);
   }
}
