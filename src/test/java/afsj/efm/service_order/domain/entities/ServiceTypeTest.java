package afsj.efm.service_order.domain.entities;

import afsj.efm.service_order.domain.enums.ServiceCategory;
import afsj.efm.service_order.domain.exceptions.InvalidServiceTypeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ServiceTypeTest {

   @Test
   @DisplayName("Should create a valid service type")
   void createValidServiceType() {
      ServiceType serviceType = new ServiceType(
              "Plantar milho",
              "Preparação do solo para o serviço",
              ServiceCategory.PLANTING
      );

      Assertions.assertEquals("Plantar milho", serviceType.serviceTypeName());
      Assertions.assertEquals("Preparação do solo para o serviço", serviceType.serviceTypeDescription());
      Assertions.assertEquals(ServiceCategory.PLANTING, serviceType.serviceTypeCategory());
   }

   @Test
   @DisplayName("Should throw error when service type name is null or blank")
   void errorWhenNameIsNullOrBlank() {
      Assertions.assertThrows(InvalidServiceTypeException.class,
              () -> new ServiceType(null, "desc", ServiceCategory.PLANTING));

      Assertions.assertThrows(InvalidServiceTypeException.class,
              () -> new ServiceType("   ", "desc", ServiceCategory.PLANTING));
   }

   @Test
   @DisplayName("Should throw error when description is null or blank")
   void errorWhenDescriptionIsNullOrBlank() {
      Assertions.assertThrows(InvalidServiceTypeException.class,
              () -> new ServiceType("Name", null, ServiceCategory.PLANTING));

      Assertions.assertThrows(InvalidServiceTypeException.class,
              () -> new ServiceType("Name", "   ", ServiceCategory.PLANTING));
   }

   @Test
   @DisplayName("Should throw error when category is null")
   void errorWhenCategoryIsNull() {
      Assertions.assertThrows(InvalidServiceTypeException.class,
              () -> new ServiceType("Name", "Description", null));
   }
}