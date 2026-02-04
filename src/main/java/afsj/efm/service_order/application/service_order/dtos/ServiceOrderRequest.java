package afsj.efm.service_order.application.service_order.dtos;

import afsj.efm.service_order.domain.enums.ServiceCategory;

public record ServiceOrderRequest(
        String employeeName,
        String farmAreaName,
        String serviceTypeName,
        String serviceTypeDescription,
        ServiceCategory serviceTypeCategory
) {
}
