package afsj.efm.service_order.application.service_order.dtos;

import java.util.UUID;

public record ServiceOrderItemResponse(
        UUID serviceOrderItemId,
        Long productId,
        String productName,
        int quantity
) {
}
