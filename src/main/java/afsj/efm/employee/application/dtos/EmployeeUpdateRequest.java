package afsj.efm.employee.application.dtos;

import jakarta.validation.constraints.Pattern;

public record EmployeeUpdateRequest(
        @Pattern(regexp = "^$|^[1-9]{2}9\\d{8}$",
                message = "Phone number must be empty or contain 11 digits (DD + 9XXXXXXXX)")
        String phoneNumber
) {
}
