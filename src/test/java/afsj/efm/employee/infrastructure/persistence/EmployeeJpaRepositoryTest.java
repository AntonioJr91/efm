package afsj.efm.employee.infrastructure.persistence;

import afsj.efm.employee.domain.entities.Cpf;
import afsj.efm.employee.domain.entities.Employee;
import afsj.efm.employee.domain.entities.PhoneNumber;
import afsj.efm.employee.domain.enums.ContractType;
import afsj.efm.employee.domain.enums.JobRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class EmployeeJpaRepositoryTest {

   String firstName = "Antonio";
   String lastName = "Sousa";
   Cpf cpf = new Cpf("39053344705");
   PhoneNumber phoneNumber = new PhoneNumber("11928374890");
   JobRole jobRole = JobRole.WORKER;
   ContractType contractType = ContractType.CLT;

   Employee employee;

   @Autowired
   EmployeeJpaRepository repository;

   @BeforeEach
   void setUp() {
      employee = new Employee(
              firstName,
              lastName,
              cpf,
              phoneNumber,
              jobRole,
              contractType
      );
   }

   @Test
   @DisplayName("Should save an employee when it is valid")
   void shouldSaveEmployee() {
      Employee saved = repository.save(employee);

      Assertions.assertNotNull(saved.getId());
      Assertions.assertEquals(firstName, saved.getFirstName());
      Assertions.assertEquals(lastName, saved.getLastName());
      Assertions.assertEquals(cpf, saved.getCpf());
      Assertions.assertEquals(phoneNumber, saved.getPhoneNumber());
      Assertions.assertEquals(jobRole, saved.getJobRole());
      Assertions.assertEquals(contractType, saved.getContractType());
   }

   @Test
   @DisplayName("Should find an employee by id when it exists")
   void shouldFindEmployeeById() {
      Employee saved = repository.save(employee);

      var result = repository.findById(saved.getId());

      Assertions.assertTrue(result.isPresent());
      Assertions.assertEquals(saved.getId(), result.get().getId());
   }

   @Test
   @DisplayName("Should return empty when searching for a non-existing id")
   void shouldReturnEmptyWhenIdDoesNotExist() {
      var result = repository.findById(999L);

      Assertions.assertTrue(result.isEmpty());
   }

   @Test
   @DisplayName("Should update employee phone number")
   void shouldUpdateEmployeePhoneNumber() {
      Employee saved = repository.save(employee);

      PhoneNumber phoneNumber = new PhoneNumber("11999999999");

      saved.changePhoneNumber(phoneNumber);

      Employee updated = repository.save(saved);

      Assertions.assertEquals(phoneNumber, updated.getPhoneNumber());
      Assertions.assertEquals(saved.getId(), updated.getId());
   }

   @Test
   @DisplayName("Should delete an employee by id")
   void shouldDeleteEmployee() {
      Employee saved = repository.save(employee);

      repository.deleteById(saved.getId());

      Assertions.assertTrue(repository.findById(saved.getId()).isEmpty());
   }

   @Test
   @DisplayName("Should return true when employee exists by CPF")
   void shouldReturnTrueWhenExistsByCpf() {
      repository.save(employee);

      Boolean exists = repository.existsByCpf(cpf);

      Assertions.assertTrue(exists);
   }

   @Test
   @DisplayName("Should return false when employee does not exist by CPF")
   void shouldReturnFalseWhenCpfDoesNotExist() {
      Boolean exists = repository.existsByCpf(new Cpf("39053344705"));

      Assertions.assertFalse(exists);
   }

   @Test
   @DisplayName("Should return true when employee exists by phone number")
   void shouldReturnTrueWhenExistsByPhoneNumber() {
      repository.save(employee);

      Boolean exists = repository.existsByPhoneNumber(phoneNumber);

      Assertions.assertTrue(exists);
   }

   @Test
   @DisplayName("Should return false when employee does not exist by phone number")
   void shouldReturnFalseWhenPhoneNumberDoesNotExist() {
      Boolean exists = repository.existsByPhoneNumber(
              new PhoneNumber("11999999999")
      );

      Assertions.assertFalse(exists);
   }

   @Test
   @DisplayName("Should remove employee phone number")
   void shouldRemoveEmployeePhoneNumber() {
      Employee saved = repository.save(employee);

      saved.changePhoneNumber(null);
      Employee updated = repository.save(saved);

      Assertions.assertNull(updated.getPhoneNumber());
      Assertions.assertNull(updated.getPhoneNumberValue());
   }
}
