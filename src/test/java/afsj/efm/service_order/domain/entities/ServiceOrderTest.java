package afsj.efm.service_order.domain.entities;

import afsj.efm.category.domain.entities.Category;
import afsj.efm.employee.domain.entities.Cpf;
import afsj.efm.employee.domain.entities.Employee;
import afsj.efm.employee.domain.entities.PhoneNumber;
import afsj.efm.employee.domain.enums.ContractType;
import afsj.efm.employee.domain.enums.JobRole;
import afsj.efm.product.domain.entities.Product;
import afsj.efm.product.domain.enums.ProductOrigin;
import afsj.efm.product.domain.enums.UnitOfMeasure;
import afsj.efm.service_order.domain.enums.ServiceCategory;
import afsj.efm.service_order.domain.enums.StatusOrder;
import afsj.efm.service_order.domain.exceptions.InvalidServiceOrderException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

class ServiceOrderTest {
   Employee employee;
   FarmArea farmArea;
   ServiceType serviceType;
   Category category;
   Product product;
   ServiceOrder serviceOrder;
   int initialQuantity = 10;

   @BeforeEach
   void setUp() {
      employee = new Employee(
              "Xibatinha",
              "xibata",
              new Cpf("39053344705"),
              new PhoneNumber("27999282716"),
              JobRole.WORKER,
              ContractType.CLT
      );

      farmArea = new FarmArea("area 51");

      serviceType = new ServiceType(
              "Plantar Milho",
              "Preparar solo",
              ServiceCategory.PLANTING
      );

      category = new Category("test");

      product = new Product(
              "test",
              initialQuantity,
              0,
              UnitOfMeasure.UNIT,
              ProductOrigin.OWN_PRODUCTION,
              category
      );

      serviceOrder = new ServiceOrder(employee, farmArea, serviceType);
   }

   @Test
   @DisplayName("Should create order when no items provided")
   void createAnOrderWithoutItems() {
      Assertions.assertEquals(employee, serviceOrder.getEmployee());
      Assertions.assertEquals(farmArea, serviceOrder.getFarmArea());
      Assertions.assertEquals(serviceType, serviceOrder.getServiceType());
      Assertions.assertEquals(StatusOrder.IN_PROGRESS, serviceOrder.getStatusOrder());
      Assertions.assertNotNull(serviceOrder.getCreatedAt());
      Assertions.assertNull(serviceOrder.getFinishedAt());

      Assertions.assertTrue(serviceOrder.getItems().isEmpty());
   }

   @Test
   @DisplayName("Should add items to order")
   void addItemsToOrder() {
      serviceOrder.addItem(product, initialQuantity);

      Assertions.assertEquals(1, serviceOrder.getItems().size());
   }

   @Test
   @DisplayName("Should increase item quantity")
   void increaseItemQuantity() {
      serviceOrder.addItem(product, 10);
      serviceOrder.increaseItemQuantity(serviceOrder.getItems().getFirst().getId(), 10);

      Assertions.assertEquals(20, serviceOrder.getItems().getFirst().getQuantity());
   }

   @Test
   @DisplayName("Should decrease item quantity")
   void decreaseItemQuantity() {
      serviceOrder.addItem(product, 10);
      serviceOrder.decreaseItemQuantity(serviceOrder.getItems().getFirst().getId(), 10);

      Assertions.assertEquals(0, serviceOrder.getItems().getFirst().getQuantity());
   }

   @Test
   @DisplayName("Should remove item")
   void removeItem() {
      serviceOrder.addItem(product, 10);

      serviceOrder.removeItem(serviceOrder.getItems().getFirst().getId());
   }

   @Test
   @DisplayName("Should throw error when trying to create an order when employee is null")
   void errorWhenEmployeeIsNull() {
      Assertions.assertThrows(InvalidServiceOrderException.class,
              () -> new ServiceOrder(null, farmArea, serviceType));
   }

   @Test
   @DisplayName("Should throw error when trying to create an order when farm area is null")
   void errorWhenFarmAreaIsNull() {
      Assertions.assertThrows(InvalidServiceOrderException.class,
              () -> new ServiceOrder(employee, null, serviceType));
   }

   @Test
   @DisplayName("Should throw error when trying to create an order when service type is null")
   void errorWhenServiceTypeIsNull() {
      Assertions.assertThrows(InvalidServiceOrderException.class,
              () -> new ServiceOrder(employee, farmArea, null));
   }

   @Test
   @DisplayName("Should throw error when terminate date is invalid")
   void errorWhenTerminateDateIsInvalid() {
      LocalDate invalidDate = LocalDate.of(2025, 1, 1);

      Assertions.assertThrows(InvalidServiceOrderException.class,
              () -> serviceOrder.complete(invalidDate));
   }

   @Test
   @DisplayName("Should throw error when order is not editable")
   void errorWhenOrderIsNotEditable() {
      serviceOrder.complete(LocalDate.now());

      Assertions.assertThrows(InvalidServiceOrderException.class,
              () -> serviceOrder.complete(LocalDate.now()));
   }

   @Test
   @DisplayName("Should throw error when trying to change order when it is canceled")
   void errorWhenTryingToChangeOrderCanceled() {
      serviceOrder.cancel(LocalDate.now());

      Assertions.assertThrows(InvalidServiceOrderException.class,
              () -> serviceOrder.complete(LocalDate.now()));
   }

   @Test
   @DisplayName("Should throw error when trying to change items if order is not in progress")
   void errorWhenTryingToChangeOrderIsNotInProgress() {
      serviceOrder.complete(LocalDate.now());

      Assertions.assertThrows(InvalidServiceOrderException.class,
              () -> serviceOrder.addItem(product, 10));
   }

   @Test
   @DisplayName("Should throw error when trying to increase or decrease items if order is not in progress")
   void errorWhenTryingToIncreaseOrDecreaseIsNotInProgress() {
      serviceOrder.addItem(product, 10);
      serviceOrder.complete(LocalDate.now());

      UUID itemId = serviceOrder.getItems().getFirst().getId();

      Assertions.assertThrows(InvalidServiceOrderException.class,
              () -> serviceOrder.increaseItemQuantity(itemId, 10));
      Assertions.assertThrows(InvalidServiceOrderException.class,
              () -> serviceOrder.decreaseItemQuantity(itemId, 10));
   }

   @Test
   @DisplayName("Should throw error when duplicate product in order ")
   void errorWhenDuplicateProductInOrder() {
      serviceOrder.addItem(product, 10);

      Assertions.assertThrows(InvalidServiceOrderException.class,
              () -> serviceOrder.addItem(product, 10));
   }
}
