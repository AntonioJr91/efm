package afsj.efm.production.application.dtos;

import java.time.LocalDate;

public record ProductionResponse(
         Long id,
         Long areaId,
         Long productId,
         Long quantity,
         String observation,
         LocalDate createdAt
) {
}
