package afsj.efm.product.application.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record IncreaseStockRequest(
        @NotNull
        @Positive
        int quantity
) {
}
