package afsj.efm.service_order.application.service_order.dtos;

import afsj.efm.service_order.domain.enums.ServiceCategory;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ServiceOrderRequest(
        @NotBlank
        @Size(min = 3, max = 50)
        String employeeName,

        @NotBlank
        @Size(min = 3, max = 50)
        String farmAreaName,

        @NotBlank
        @Size(min = 3, max = 50)
        String serviceTypeName,

        @NotBlank
        @Size(min = 3, max = 255)
        String serviceTypeDescription,

        @NotNull
        @Enumerated
        ServiceCategory serviceTypeCategory
) {
}
