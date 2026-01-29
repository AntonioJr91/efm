package afsj.efm.employee.infrastructure.persistence;

import afsj.efm.employee.domain.entities.Cpf;
import afsj.efm.employee.domain.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeJpaRepository extends JpaRepository<Employee, Long> {
   Boolean existsByCpf(Cpf cpf);
}
