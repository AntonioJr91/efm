package afsj.efm.product.application.dtos;

public record StockUpdateResponse(
        Long productId,
        String productName,
        int quantity
) {
}
