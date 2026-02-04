package afsj.efm.service_order.domain.entities;

import afsj.efm.service_order.domain.enums.ServiceCategory;
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
        String name,

        @NotBlank
        @Size(min = 3, max = 255)
        String description,

        @NotNull
        @Enumerated(EnumType.STRING)
        ServiceCategory serviceCategory
) {
   public ServiceType {
      if (name == null || name.isBlank())
         throw new IllegalArgumentException("SERVICE_TYPE_NAME_REQUIRED");
      if (description == null || description.isBlank())
         throw new IllegalArgumentException("SERVICE_TYPE_DESCRIPTION_REQUIRED");
      if (serviceCategory == null)
         throw new IllegalArgumentException("SERVICE_CATEGORY_REQUIRED");
   }
}
