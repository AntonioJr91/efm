package afsj.efm.service_order.application.service_order.usecases;

import afsj.efm.employee.application.errors.EmployeeNotFound;
import afsj.efm.employee.infrastructure.persistence.EmployeeJpaRepository;
import afsj.efm.service_order.application.farm_area.errors.FarmAreaNotFound;
import afsj.efm.service_order.application.service_order.dtos.ServiceOrderRequest;
import afsj.efm.service_order.application.service_order.dtos.ServiceOrderResponse;
import afsj.efm.service_order.application.service_order.errors.ServiceOrderConflict;
import afsj.efm.service_order.application.service_order.mappers.ServiceOrderMapper;
import afsj.efm.service_order.domain.entities.ServiceOrder;
import afsj.efm.service_order.domain.entities.ServiceType;
import afsj.efm.service_order.infrastructure.persistence.FarmAreaJpaRepository;
import afsj.efm.service_order.infrastructure.persistence.ServiceOrderJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateServiceOrderUseCase {

   private final ServiceOrderJpaRepository repository;
   private final EmployeeJpaRepository employeeJpaRepository;
   private final FarmAreaJpaRepository farmAreaJpaRepository;

   public CreateServiceOrderUseCase(
           ServiceOrderJpaRepository repository,
           EmployeeJpaRepository employeeJpaRepository,
           FarmAreaJpaRepository farmAreaJpaRepository
   ) {
      this.repository = repository;
      this.employeeJpaRepository = employeeJpaRepository;
      this.farmAreaJpaRepository = farmAreaJpaRepository;
   }

   @Transactional
   public ServiceOrderResponse execute(ServiceOrderRequest request) {
      var employee = employeeJpaRepository.findById(request.employeeId())
              .orElseThrow(EmployeeNotFound::byId);

      var farmArea = farmAreaJpaRepository.findById(request.farmAreaId())
              .orElseThrow(FarmAreaNotFound::byId);

      if (repository.existsByServiceTypeServiceTypeName(request.serviceTypeName()))
         throw ServiceOrderConflict.serviceOrderAlreadyExists();

      var serviceType = new ServiceType(
              request.serviceTypeName(),
              request.serviceTypeDescription(),
              request.serviceTypeCategory()
      );

      var serviceOrder = new ServiceOrder(employee, farmArea, serviceType);

      repository.save(serviceOrder);

      return ServiceOrderMapper.toDto(serviceOrder);
   }
}
