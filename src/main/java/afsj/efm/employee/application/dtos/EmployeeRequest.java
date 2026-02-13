package afsj.efm.employee.application.dtos;

import afsj.efm.employee.domain.enums.ContractType;
import afsj.efm.employee.domain.enums.JobRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record EmployeeRequest(
        @NotBlank
        @Size(min = 3, max = 50)
        String firstName,

        @NotBlank
        @Size(min = 3, max = 50)
        String lastName,

        @NotNull
        @Size(min = 11, max = 11)
        @CPF
        String cpf,

        @Pattern(regexp = "^$|^[1-9]{2}9\\d{8}$",
                message = "Phone number must be empty or contain 11 digits (DD + 9XXXXXXXX)")
        String phoneNumber,

        @NotNull
        JobRole jobRole,

        @NotNull
        ContractType contractType
) {
}
