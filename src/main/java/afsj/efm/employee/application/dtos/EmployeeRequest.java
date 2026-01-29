package afsj.efm.employee.application.dtos;

import afsj.efm.employee.domain.enums.ContractType;
import afsj.efm.employee.domain.enums.JobRole;
import afsj.efm.employee.domain.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

public record EmployeeRequest(
        @NotBlank
        @Size(min = 3, max = 50)
        String firstName,

        @NotBlank
        @Size(min = 3, max = 50)
        String lastName,

        @NotNull
        @Size(min = 11, max = 11)
        String cpf,

        @Size(min = 11, max = 11)
        String phoneNumber,

        @NotNull
        JobRole jobRole,

        @NotNull
        ContractType contractType
) {
}
