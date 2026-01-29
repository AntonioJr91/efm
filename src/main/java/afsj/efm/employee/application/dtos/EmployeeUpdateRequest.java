package afsj.efm.employee.application.dtos;

import jakarta.validation.constraints.Size;

public record EmployeeUpdateRequest(
         @Size(min = 3, max = 50)
        String phoneNumber
) {
}
