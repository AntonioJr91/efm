package afsj.efm.employee.application.usecases;

import afsj.efm.employee.application.errors.EmployeeNotFound;
import afsj.efm.employee.infrastructure.persistence.EmployeeJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteEmployeeUseCase {

   private final EmployeeJpaRepository repository;

   public DeleteEmployeeUseCase(EmployeeJpaRepository repository) {
      this.repository = repository;
   }

   @Transactional
   public void execute(Long id) {
      if (!repository.existsById(id)) throw EmployeeNotFound.byId();
      repository.deleteById(id);
   }
}
