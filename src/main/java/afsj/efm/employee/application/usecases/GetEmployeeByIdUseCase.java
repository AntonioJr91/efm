package afsj.efm.employee.application.usecases;

import afsj.efm.employee.application.dtos.EmployeeResponse;
import afsj.efm.employee.application.errors.EmployeeNotFound;
import afsj.efm.employee.application.mappers.EmployeeMapper;
import afsj.efm.employee.infrastructure.persistence.EmployeeJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetEmployeeByIdUseCase {
   private final EmployeeJpaRepository repository;

   public GetEmployeeByIdUseCase(EmployeeJpaRepository repository) {
      this.repository = repository;
   }

   @Transactional(readOnly = true)
   public EmployeeResponse execute(Long id) {
      var employee = repository.findById(id)
              .orElseThrow(() -> EmployeeNotFound.byId(id));

      return EmployeeMapper.toDto(employee);
   }
}
