package afsj.efm.service_order.infrastructure.persistence;

import afsj.efm.category.domain.entities.Category;
import afsj.efm.employee.domain.entities.Cpf;
import afsj.efm.employee.domain.entities.Employee;
import afsj.efm.employee.domain.entities.PhoneNumber;
import afsj.efm.employee.domain.enums.ContractType;
import afsj.efm.employee.domain.enums.JobRole;
import afsj.efm.product.domain.entities.Product;
import afsj.efm.product.domain.enums.UnitOfMeasure;
import afsj.efm.service_order.domain.entities.FarmArea;
import afsj.efm.service_order.domain.entities.ServiceOrder;
import afsj.efm.service_order.domain.entities.ServiceType;
import afsj.efm.service_order.domain.enums.ServiceCategory;
import afsj.efm.service_order.domain.enums.StatusOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;


@DataJpaTest
class ServiceOrderJpaRepositoryTest {

   @Autowired
   private ServiceOrderJpaRepository repository;

   @Autowired
   private TestEntityManager entityManager;

   private ServiceOrder saved;

   @BeforeEach
   void setUp() {

      Employee employee = entityManager.persist(
              new Employee(
                      "Xibatinha",
                      "xibata",
                      new Cpf("39053344705"),
                      new PhoneNumber("27999282716"),
                      JobRole.WORKER,
                      ContractType.CLT
              )
      );

      FarmArea farmArea = entityManager.persist(
              new FarmArea("area 51")
      );

      ServiceType serviceType = new ServiceType(
              "Plantar Milho",
              "Preparar solo",
              ServiceCategory.PLANTING
      );

      saved = entityManager.persist(
              new ServiceOrder(employee, farmArea, serviceType)
      );

      entityManager.flush();
      entityManager.clear();
   }

   @Test
   @DisplayName("Should find service order by id")
   void findById() {

      ServiceOrder found = repository.findById(saved.getId())
              .orElseThrow();

      Assertions.assertEquals(saved.getId(), found.getId());
      Assertions.assertEquals(StatusOrder.IN_PROGRESS, found.getStatusOrder());
   }

   @Test
   @DisplayName("Should return empty when id does not exist")
   void returnEmptyWhenIdDoesNotExist() {

      var result = repository.findById(999L);

      Assertions.assertTrue(result.isEmpty());
   }

   @Test
   @DisplayName("Should delete service order")
   void deleteServiceOrder() {

      repository.deleteById(saved.getId());
      entityManager.flush();

      var result = repository.findById(saved.getId());

      Assertions.assertTrue(result.isEmpty());
   }

   @Test
   @DisplayName("Should update service order status")
   void updateServiceOrder() {

      ServiceOrder order = repository.findById(saved.getId())
              .orElseThrow();

      order.cancel(LocalDate.now());

      repository.save(order);
      entityManager.flush();
      entityManager.clear();

      ServiceOrder updated = repository.findById(saved.getId())
              .orElseThrow();

      Assertions.assertEquals(StatusOrder.CANCELED, updated.getStatusOrder());
      Assertions.assertNotNull(updated.getFinishedAt());
   }

   @Test
   @DisplayName("Should cascade persist items")
   void shouldPersistItemsWithCascade() {

      ServiceOrder order = repository.findById(saved.getId())
              .orElseThrow();

      Product product = entityManager.persist(
              new Product(
                      "Produto Teste",
                      10,
                      UnitOfMeasure.UNIT,
                      entityManager.persist(new Category("Categoria Teste"))
              )
      );

      order.addItem(product, 5);

      repository.save(order);
      entityManager.flush();
      entityManager.clear();

      ServiceOrder updated = repository.findById(saved.getId())
              .orElseThrow();

      Assertions.assertEquals(1, updated.getItems().size());
   }
}
