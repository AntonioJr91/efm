package afsj.efm.shared.web;

import afsj.efm.employee.domain.enums.ContractType;
import afsj.efm.employee.domain.enums.JobRole;
import afsj.efm.employee.domain.enums.Status;
import afsj.efm.product.domain.enums.MovementType;
import afsj.efm.product.domain.enums.UnitOfMeasure;
import afsj.efm.service_order.domain.enums.ServiceCategory;
import afsj.efm.service_order.domain.enums.StatusOrder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/enums")
public class EnumsController {

   @GetMapping("/contract-types")
   public List<String> getContractTypes() {
      return Arrays.stream(ContractType.values())
              .map(Enum::name)
              .toList();
   }

   @GetMapping("/job-roles")
   public List<String> getJobRoles() {
      return Arrays.stream(JobRole.values())
              .map(Enum::name)
              .toList();
   }

   @GetMapping("/statuses")
   public List<String> getStatuses() {
      return Arrays.stream(Status.values())
              .map(Enum::name)
              .toList();
   }

   @GetMapping("/movement-types")
   public List<String> getMovementTypes() {
      return Arrays.stream(MovementType.values())
              .map(Enum::name)
              .toList();
   }

   @GetMapping("/unit-of-measure")
   public List<String> getUnitOfMeasure() {
      return Arrays.stream(UnitOfMeasure.values())
              .map(Enum::name)
              .toList();
   }

   @GetMapping("/service-categories")
   public List<String> getServiceCategories() {
      return Arrays.stream(ServiceCategory.values())
              .map(Enum::name)
              .toList();
   }

   @GetMapping("/status-orders")
   public List<String> getStatusOrders() {
      return Arrays.stream(StatusOrder.values())
              .map(Enum::name)
              .toList();
   }
}
