package afsj.efm.employee.application.dtos;

import afsj.efm.employee.domain.enums.ContractType;
import afsj.efm.employee.domain.enums.JobRole;
import afsj.efm.employee.domain.enums.Status;

import java.time.LocalDate;

public record EmployeeResponse(
        Long id,
        String firstName,
        String lastName,
        String cpf,
        String phoneNumber,
        JobRole jobRole,
        ContractType contractType,
        LocalDate hireDate,
        Status status,
        LocalDate terminationDate
) {
}
