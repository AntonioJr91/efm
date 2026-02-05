package afsj.efm.service_order.application.service_order.dtos;

public record AddItemToServiceOrderRequest(
        Long serviceOrderId,
        Long productId,
        int quantity
) {
}
