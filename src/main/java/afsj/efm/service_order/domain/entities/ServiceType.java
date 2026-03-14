package afsj.efm.service_order.domain.entities;

import afsj.efm.service_order.domain.enums.ServiceCategory;
import afsj.efm.service_order.domain.exceptions.InvalidServiceTypeException;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Embeddable
public record ServiceType(
        @NotBlank
        @Size(min = 3, max = 50)
        String serviceTypeName,

        @Size(max = 255)
        String serviceTypeDescription,

        @NotNull
        @Enumerated(EnumType.STRING)
        ServiceCategory serviceTypeCategory
) {
   public ServiceType {
      if (serviceTypeName == null || serviceTypeName.isBlank())
         throw new InvalidServiceTypeException("SERVICE_TYPE_NAME_REQUIRED");
      if (serviceTypeCategory == null)
         throw new InvalidServiceTypeException("SERVICE_CATEGORY_REQUIRED");
   }
}
