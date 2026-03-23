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
import afsj.efm.service_order.domain.exceptions.InvalidServiceOrderItemException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ServiceOrderItemTest {

   Employee employee;
   FarmArea farmArea;
   ServiceType serviceType;
   Category category;
   Product product;
   ServiceOrder serviceOrder;

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
              10,
              0,
              UnitOfMeasure.UNIT,
              ProductOrigin.OWN_PRODUCTION,
              category
      );

      serviceOrder = new ServiceOrder(employee, farmArea, serviceType);
   }

   @Test
   @DisplayName("Should create an order item without items")
   void createAnOrderItemWithoutItems() {
      Assertions.assertTrue(serviceOrder.getItems().isEmpty());
   }

   @Test
   @DisplayName("Should create an order with items")
   void createAnOrderItemWithItems() {
      serviceOrder.addItem(product, 10);
      Assertions.assertEquals(1, serviceOrder.getItems().size());

      ServiceOrderItem item = serviceOrder.getItems().getFirst();
      Assertions.assertEquals(product, item.getProduct());
      Assertions.assertEquals(10, item.getQuantity());
   }

   @Test
   @DisplayName("Should increase quantity")
   void increaseQuantity() {
      serviceOrder.addItem(product, 10);
      ServiceOrderItem item = serviceOrder.getItems().getFirst();
      item.increase(10);

      Assertions.assertEquals(20, item.getQuantity());
   }

   @Test
   @DisplayName("Should decrease quantity")
   void decreaseQuantity() {
      serviceOrder.addItem(product, 20);
      ServiceOrderItem item = serviceOrder.getItems().getFirst();
      item.decrease(10);

      Assertions.assertEquals(10, item.getQuantity());
   }

   @Test
   @DisplayName("Should throw error when service order is null")
   void errorWhenServiceOrderIsNull() {
      Assertions.assertThrows(InvalidServiceOrderItemException.class,
              () -> new ServiceOrderItem(null, product, 10));
   }

   @Test
   @DisplayName("Should throw error when product is null")
   void errorWhenServiceProductIsNull() {
      Assertions.assertThrows(InvalidServiceOrderItemException.class,
              () -> new ServiceOrderItem(serviceOrder, null, 10));
   }

   @Test
   @DisplayName("Should throw error when quantity is equal or less than zero")
   void errorWhenServiceQuantityIsEqualOrLessThanZero() {
      Assertions.assertThrows(InvalidServiceOrderItemException.class,
              () -> new ServiceOrderItem(serviceOrder, product, 0));
      Assertions.assertThrows(InvalidServiceOrderItemException.class,
              () -> new ServiceOrderItem(serviceOrder, product, -1));
   }

   @Test
   @DisplayName("Should throw error when try increase negative or number zero")
   void errorWhenTryIncreaseWithNumberNegativeOrZero() {
      serviceOrder.addItem(product, 10);
      ServiceOrderItem item = serviceOrder.getItems().getFirst();

      Assertions.assertThrows(InvalidServiceOrderItemException.class,
              () -> item.increase(0));

      Assertions.assertThrows(InvalidServiceOrderItemException.class,
              () -> item.increase(-10));
   }

   @Test
   @DisplayName("Should throw error when try decrease negative or number zero")
   void errorWhenTryDecreaseWithNumberNegativeOrZero() {
      serviceOrder.addItem(product, 10);
      ServiceOrderItem item = serviceOrder.getItems().getFirst();

      Assertions.assertThrows(InvalidServiceOrderItemException.class,
              () -> item.decrease(0));

      Assertions.assertThrows(InvalidServiceOrderItemException.class,
              () -> item.decrease(-10));
   }

   @Test
   @DisplayName("Should allow decrease quantity to zero")
   void allowDecreaseQuantityToZero() {
      serviceOrder.addItem(product, 10);
      ServiceOrderItem item = serviceOrder.getItems().getFirst();

      item.decrease(10);

      Assertions.assertEquals(0, item.getQuantity());
   }

   @Test
   @DisplayName("Should throw error when try decrease quantity greater than current quantity")
   void errorWhenTryDecreaseQuantityGreaterThanProductStock() {
      serviceOrder.addItem(product, 10);
      ServiceOrderItem item = serviceOrder.getItems().getFirst();

      Assertions.assertThrows(InvalidServiceOrderItemException.class,
              () -> item.decrease(11));
   }
}
