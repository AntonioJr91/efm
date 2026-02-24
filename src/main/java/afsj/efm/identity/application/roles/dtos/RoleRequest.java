package afsj.efm.identity.application.roles.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RoleRequest(
        @NotBlank
        @Size(min = 3, max = 50)
        String roleName
) {
}
