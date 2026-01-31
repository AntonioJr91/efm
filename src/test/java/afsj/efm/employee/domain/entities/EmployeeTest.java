package afsj.efm.employee.domain.entities;

import afsj.efm.employee.domain.enums.ContractType;
import afsj.efm.employee.domain.enums.JobRole;
import afsj.efm.employee.domain.enums.Status;
import afsj.efm.employee.domain.exceptions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

   @Test
   @DisplayName("Should create employee when all required data is valid")
   void shouldCreateEmployeeWhenDataIsValid() {
      Employee employee = new Employee(
              "Antonio",
              "Sousa",
              new Cpf("39053344705"),
              new PhoneNumber("27999282715"),
              JobRole.ADMINISTRATOR,
              ContractType.CLT
      );

      assertEquals("Antonio", employee.getFirstName());
      assertEquals("Sousa", employee.getLastName());
      assertNotNull(employee.getCpf());
      assertEquals("27999282715", employee.getPhoneNumberValue());
      assertEquals(Status.ACTIVE, employee.getStatus());
      assertEquals(LocalDate.now(), employee.getHireDate());
   }

   @Test
   @DisplayName("Should throw error when first name is null")
   void shouldThrowErrorWhenFirstNameIsNull() {
      assertThrows(InvalidEmployeeNameException.class, () ->
              new Employee(
                      null,
                      "Sousa",
                      new Cpf("39053344705"),
                      new PhoneNumber("27999282715"),
                      JobRole.ADMINISTRATOR,
                      ContractType.CLT
              )
      );
   }

   @Test
   @DisplayName("Should throw error when first name is too short")
   void shouldThrowErrorWhenFirstNameIsTooShort() {
      assertThrows(InvalidEmployeeNameException.class, () ->
              new Employee(
                      "An",
                      "Sousa",
                      new Cpf("39053344705"),
                      new PhoneNumber("27999282715"),
                      JobRole.ADMINISTRATOR,
                      ContractType.CLT
              )
      );
   }

   @Test
   @DisplayName("Should throw error when last name is null")
   void shouldThrowErrorWhenLastNameIsNull() {
      assertThrows(InvalidEmployeeNameException.class, () ->
              new Employee(
                      "Antonio",
                      null,
                      new Cpf("39053344705"),
                      new PhoneNumber("27999282715"),
                      JobRole.ADMINISTRATOR,
                      ContractType.CLT
              )
      );
   }

   @Test
   @DisplayName("Should throw error when cpf is null")
   void shouldThrowErrorWhenCpfIsNull() {
      assertThrows(InvalidCpfException.class, () ->
              new Employee(
                      "Antonio",
                      "Sousa",
                      null,
                      new PhoneNumber("27999282715"),
                      JobRole.ADMINISTRATOR,
                      ContractType.CLT
              )
      );
   }

   @Test
   @DisplayName("Should throw error when job role is null")
   void shouldThrowErrorWhenJobRoleIsNull() {
      assertThrows(InvalidJobRoleException.class, () ->
              new Employee(
                      "Antonio",
                      "Sousa",
                      new Cpf("39053344705"),
                      new PhoneNumber("27999282715"),
                      null,
                      ContractType.CLT
              )
      );
   }

   @Test
   @DisplayName("Should throw error when contract type is null")
   void shouldThrowErrorWhenContractTypeIsNull() {
      assertThrows(InvalidContractTypeException.class, () ->
              new Employee(
                      "Antonio",
                      "Sousa",
                      new Cpf("39053344705"),
                      new PhoneNumber("27999282715"),
                      JobRole.ADMINISTRATOR,
                      null
              )
      );
   }

   @Test
   @DisplayName("Should allow null value for JPA hydration")
   void shouldCreateEmployeeWithoutPhoneNumberWhenPhoneIsNull() {
      Employee employee = new Employee(
              "Antonio",
              "Sousa",
              new Cpf("39053344705"),
              null,
              JobRole.ADMINISTRATOR,
              ContractType.CLT
      );

      assertNull(employee.getPhoneNumber());
      assertNull(employee.getPhoneNumberValue());
   }

   @Test
   @DisplayName("Should update phone number when new phone is valid")
   void shouldUpdatePhoneNumberWhenIsValid() {
      Employee employee = new Employee(
              "Antonio",
              "Sousa",
              new Cpf("39053344705"),
              null,
              JobRole.ADMINISTRATOR,
              ContractType.CLT
      );
      PhoneNumber phoneNumber = new PhoneNumber("27999282715");

      employee.changePhoneNumber(phoneNumber);

      assertEquals("27999282715", employee.getPhoneNumberValue());
   }

   @Test
   @DisplayName("Should terminate employee when termination date is valid")
   void shouldTerminateEmployeeWhenDateIsValid() {
      Employee employee = new Employee(
              "Antonio",
              "Sousa",
              new Cpf("39053344705"),
              null,
              JobRole.ADMINISTRATOR,
              ContractType.CLT
      );

      LocalDate terminationDate = LocalDate.now().plusDays(1);

      employee.terminate(terminationDate);

      assertEquals(Status.INACTIVE, employee.getStatus());
      assertEquals(terminationDate, employee.getTerminationDate());
   }

   @Test
   @DisplayName("Should throw error when termination date is before hire date")
   void shouldThrowErrorWhenTerminationDateIsBeforeHireDate() {
      Employee employee = new Employee(
              "Antonio",
              "Sousa",
              new Cpf("39053344705"),
              null,
              JobRole.ADMINISTRATOR,
              ContractType.CLT
      );

      assertThrows(InvalidTerminationDateException.class, () ->
              employee.terminate(LocalDate.now())
      );
   }

   @Test
   @DisplayName("Should throw error when employee is already terminated")
   void shouldThrowErrorWhenEmployeeIsAlreadyTerminated() {
      Employee employee = new Employee(
              "Antonio",
              "Sousa",
              new Cpf("39053344705"),
              null,
              JobRole.ADMINISTRATOR,
              ContractType.CLT
      );

      employee.terminate(LocalDate.now().plusDays(1));

      assertThrows(InvalidTerminationDateException.class, () ->
              employee.terminate(LocalDate.now().plusDays(2))
      );
   }

   @Test
   @DisplayName("Should return true when employee is active")
   void shouldReturnTrueWhenEmployeeIsActive() {
      Employee employee = new Employee(
              "Antonio",
              "Sousa",
              new Cpf("39053344705"),
              null,
              JobRole.ADMINISTRATOR,
              ContractType.CLT
      );

      assertTrue(employee.isActive());
   }
}
