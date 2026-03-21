package afsj.efm.product.application.dtos;

import afsj.efm.product.domain.enums.ProductOrigin;
import afsj.efm.product.domain.enums.StockStatus;
import afsj.efm.product.domain.enums.UnitOfMeasure;

import java.time.LocalDate;

public record ProductReportResponse(
        Long id,
        String name,
        String categoryName,
        int stock,
        int minimumStock,
        StockStatus stockStatus,
        UnitOfMeasure unitOfMeasure,
        ProductOrigin productOrigin,
        LocalDate createdAt
) {
}
