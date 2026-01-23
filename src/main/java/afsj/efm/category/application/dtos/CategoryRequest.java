package afsj.efm.category.application.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequest(
        @NotBlank
        @Size(min = 3, max = 50)
        String name
) {
}
