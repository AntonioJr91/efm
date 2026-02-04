package afsj.efm.service_order.application.service_order.dtos;

import java.time.LocalDate;
import java.util.List;

public record ServiceOrderDetailResponse(
        Long id,
        String employeeName,
        String employeeJobRole,
        String farmAreaName,
        String serviceTypeName,
        String serviceTypeDescription,
        String serviceTypeCategory,
        String status,
        LocalDate createdAt,
        LocalDate finishedAt,
        List<ServiceOrderItemResponse> items
) {
}
