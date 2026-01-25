package afsj.efm.product.application.dtos;

import afsj.efm.product.domain.enums.UnitOfMeasure;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductRequest(
        @NotBlank
        @Size(min = 3, max = 50)
        String name,

        @NotNull
        @Min(1)
        int stock,

        @NotNull
        UnitOfMeasure unitOfMeasure
) {
}
