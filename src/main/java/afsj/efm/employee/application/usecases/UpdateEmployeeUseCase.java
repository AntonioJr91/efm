package afsj.efm.employee.application.usecases;

import afsj.efm.employee.application.dtos.EmployeeUpdateRequest;
import afsj.efm.employee.application.dtos.EmployeeUpdateResponse;
import afsj.efm.employee.application.errors.EmployeeNotFound;
import afsj.efm.employee.application.mappers.EmployeeMapper;
import afsj.efm.employee.infrastructure.persistence.EmployeeJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateEmployeeUseCase {

   private final EmployeeJpaRepository repository;

   public UpdateEmployeeUseCase(EmployeeJpaRepository repository) {
      this.repository = repository;
   }

   @Transactional
   public EmployeeUpdateResponse execute(Long id, EmployeeUpdateRequest request) {
      var employee = repository.findById(id)
              .orElseThrow(() -> EmployeeNotFound.byId(id));

      employee.changePhoneNumber(request.phoneNumber());

      return EmployeeMapper.toDtoUpdate(employee);
   }
}
