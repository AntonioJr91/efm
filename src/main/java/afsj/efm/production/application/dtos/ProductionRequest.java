package afsj.efm.production.application.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductionRequest(
        @NotNull
        Long areaId,
        @NotNull
        Long productId,
        @NotNull
        Long quantity,
        @Size(max = 255)
        String observation
) {
}
