package afsj.efm.service_order.application.service_order.dtos;

public record ServiceOrderItemResponse(
        Long productId,
        String productName,
        int quantity
) {
}
