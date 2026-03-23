package afsj.efm.product.application.dtos;

import afsj.efm.product.domain.enums.ProductOrigin;
import afsj.efm.product.domain.enums.StockStatus;
import afsj.efm.product.domain.enums.UnitOfMeasure;

import java.time.LocalDate;

public record ProductResponse(
        Long id,
        String name,
        Integer stock,
        Integer minimumStock,
        StockStatus stockStatus,
        UnitOfMeasure unitOfMeasure,
        ProductOrigin productOrigin,
        LocalDate createdAt,
        Long categoryId
) {
}
