package afsj.efm.service_order.application.service_order.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AddItemToServiceOrderRequest(
        @NotNull
        Long serviceOrderId,

        @NotNull
        Long productId,

        @NotNull
        @Positive
        int quantity
) {
}
