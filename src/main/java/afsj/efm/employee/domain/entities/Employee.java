package afsj.efm.employee.domain.entities;

import afsj.efm.employee.domain.enums.ContractType;
import afsj.efm.employee.domain.enums.JobRole;
import afsj.efm.employee.domain.enums.Status;
import afsj.efm.employee.domain.exceptions.*;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "employees")
public class Employee {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(nullable = false, updatable = false)
   private String firstName;

   @Column(nullable = false, updatable = false)
   private String lastName;

   @Embedded
   private Cpf cpf;

   @Embedded
   private PhoneNumber phoneNumber;

   @Enumerated(EnumType.STRING)
   @Column(nullable = false, updatable = false)
   private JobRole jobRole;

   @Enumerated(EnumType.STRING)
   @Column(nullable = false, updatable = false)
   private ContractType contractType;

   @Column(nullable = false, updatable = false)
   private LocalDate hireDate;

   @Column(nullable = true, updatable = true)
   private LocalDate terminationDate;

   @Enumerated(EnumType.STRING)
   @Column(nullable = false, updatable = true)
   private Status status;

   protected Employee() {
   }

   public Employee(String firstName, String lastName, Cpf cpf, PhoneNumber phoneNumber,
                   JobRole jobRole, ContractType contractType) {
      validateName(firstName, lastName);
      validateCpf(cpf);
      validateJobRole(jobRole);
      validateContractType(contractType);

      this.firstName = firstName.trim();
      this.lastName = lastName.trim();
      this.cpf = cpf;
      this.phoneNumber = phoneNumber;
      this.jobRole = jobRole;
      this.contractType = contractType;
      this.hireDate = LocalDate.now();
      this.status = Status.ACTIVE;
   }

   public Long getId() {
      return id;
   }

   public String getFirstName() {
      return firstName;
   }

   public String getLastName() {
      return lastName;
   }

   public Cpf getCpf() {
      return cpf;
   }

   public PhoneNumber getPhoneNumber() {
      return phoneNumber == null ? null : phoneNumber;
   }

   public String getPhoneNumberValue() {
      return phoneNumber == null ? null : phoneNumber.value();
   }

   public void changePhoneNumber(PhoneNumber phoneNumber) {
      this.phoneNumber = phoneNumber;
   }

   public JobRole getJobRole() {
      return jobRole;
   }

   public ContractType getContractType() {
      return contractType;
   }

   public LocalDate getHireDate() {
      return hireDate;
   }

   public LocalDate getTerminationDate() {
      return terminationDate;
   }

   public Status getStatus() {
      return status;
   }

   public void terminate(LocalDate terminationDate) {
      validateTerminationDate(terminationDate);

      this.terminationDate = terminationDate;
      this.status = Status.INACTIVE;
   }

   public boolean isActive() {
      return status == Status.ACTIVE;
   }

   private void validateName(String firstName, String lastName) {
      if (firstName == null) throw new InvalidEmployeeNameException("FIRST_NAME_IS_REQUIRED");
      if (firstName.length() < 3) throw new InvalidEmployeeNameException("FIRST_NAME_TOO_SHORT");
      if (firstName.length() > 50) throw new InvalidEmployeeNameException("FIRST_NAME_TOO_LONG");

      if (lastName == null) throw new InvalidEmployeeNameException("LAST_NAME_IS_REQUIRED");
      if (lastName.length() < 3) throw new InvalidEmployeeNameException("LAST_NAME_TOO_SHORT");
      if (lastName.length() > 50) throw new InvalidEmployeeNameException("LAST_NAME_TOO_LONG");
   }

   private void validateCpf(Cpf cpf) {
      if (cpf == null) throw new InvalidCpfException("CPF_IS_REQUIRED");
   }

   private void validateJobRole(JobRole jobRole) {
      if (jobRole == null) throw new InvalidJobRoleException("JOB_ROLE_IS_REQUIRED");
   }

   private void validateContractType(ContractType contractType) {
      if (contractType == null) throw new InvalidContractTypeException("CONTRACT_TYPE_IS_REQUIRED");
   }

   private void validateTerminationDate(LocalDate terminationDate) {
      if (terminationDate == null) throw new InvalidTerminationDateException("TERMINATION_DATE_IS_REQUIRED");
      if (!terminationDate.isAfter(hireDate))
         throw new InvalidTerminationDateException("TERMINATION_DATE_MUST_BE_AFTER_HIRE_DATE");
      if (this.status == Status.INACTIVE) throw new InvalidTerminationDateException("EMPLOYEE_ALREADY_TERMINATED");
   }
}
