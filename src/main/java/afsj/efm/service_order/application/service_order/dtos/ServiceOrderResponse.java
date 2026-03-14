package afsj.efm.service_order.application.service_order.dtos;

import java.time.LocalDate;

public record ServiceOrderResponse(
        Long id,
        Long employeeId,
        Long farmAreaId,
        String serviceTypeName,
        String status,
        LocalDate createdAt
) {
}
