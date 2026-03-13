package afsj.efm.employee.application.usecases;

import afsj.efm.employee.application.dtos.EmployeeResponse;
import afsj.efm.employee.application.errors.EmployeeNotFound;
import afsj.efm.employee.application.mappers.EmployeeMapper;
import afsj.efm.employee.infrastructure.persistence.EmployeeJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class TerminationEmployeeUseCase {

   private final EmployeeJpaRepository repository;

   public TerminationEmployeeUseCase(EmployeeJpaRepository repository) {
      this.repository = repository;
   }

   @Transactional
   public EmployeeResponse execute(Long id) {
      var emp = repository.findById(id)
              .orElseThrow(() -> EmployeeNotFound.byId(id));
      emp.terminate(LocalDate.now());
      return EmployeeMapper.toDto(emp);
   }

}
