package afsj.efm.production.application.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProductionRequest(
        @NotNull
        Long areaId,

        @NotNull
        Long productId,

        @Min(1)
        int quantity,

        @Size(max = 255)
        String observation
) {
}
