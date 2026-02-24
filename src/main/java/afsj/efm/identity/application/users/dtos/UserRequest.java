package afsj.efm.identity.application.users.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotBlank
        @Size(min = 3, max = 50)
        String username,

        @NotBlank
        @Size(min = 3, max = 255)
        String password
) {
}
