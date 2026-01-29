package afsj.efm.employee.application.usecases;

import afsj.efm.employee.application.dtos.EmployeeRequest;
import afsj.efm.employee.application.dtos.EmployeeResponse;
import afsj.efm.employee.application.errors.EmployeeConflicts;
import afsj.efm.employee.application.mappers.EmployeeMapper;
import afsj.efm.employee.domain.entities.Cpf;
import afsj.efm.employee.domain.entities.Employee;
import afsj.efm.employee.infrastructure.persistence.EmployeeJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateEmployeeUseCase {

   private final EmployeeJpaRepository repository;

   public CreateEmployeeUseCase(EmployeeJpaRepository repository) {
      this.repository = repository;
   }

   @Transactional
   public EmployeeResponse execute(EmployeeRequest request) {
      Cpf newCpf = new Cpf(request.cpf());

      if(repository.existsByCpf(newCpf)) throw EmployeeConflicts.cpfAlreadyExists(newCpf);

      Employee newEmployee = new Employee(
              request.firstName(),
              request.lastName(),
              newCpf,
              request.phoneNumber(),
              request.jobRole(),
              request.contractType()
      );

      Employee saved = repository.save(newEmployee);

      return EmployeeMapper.toDto(saved);
   }
}
