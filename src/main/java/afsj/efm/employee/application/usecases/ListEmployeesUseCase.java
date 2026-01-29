package afsj.efm.employee.application.usecases;

import afsj.efm.employee.application.dtos.EmployeeResponse;
import afsj.efm.employee.application.mappers.EmployeeMapper;
import afsj.efm.employee.infrastructure.persistence.EmployeeJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ListEmployeesUseCase {
   private final EmployeeJpaRepository repository;

   public ListEmployeesUseCase(EmployeeJpaRepository repository) {
      this.repository = repository;
   }

   @Transactional(readOnly = true)
   public List<EmployeeResponse> execute() {
      return EmployeeMapper.toDtoList(repository.findAll());
   }
}
