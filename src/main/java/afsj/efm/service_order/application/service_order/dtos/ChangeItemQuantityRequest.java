package afsj.efm.service_order.application.service_order.dtos;

import jakarta.validation.constraints.Min;

public record ChangeItemQuantityRequest(
        @Min(1)
        int quantity
) {
}
