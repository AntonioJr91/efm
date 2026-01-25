package afsj.efm.product.application.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DecreaseStockRequest(
        @NotNull
        @Positive
        int quantity
) {
}
