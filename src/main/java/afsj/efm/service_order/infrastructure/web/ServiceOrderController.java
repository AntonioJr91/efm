package afsj.efm.service_order.infrastructure.web;

import afsj.efm.service_order.application.service_order.dtos.ServiceOrderRequest;
import afsj.efm.service_order.application.service_order.dtos.ServiceOrderResponse;
import afsj.efm.service_order.application.service_order.usecases.CreateServiceOrderUseCase;
import afsj.efm.service_order.application.service_order.usecases.DeleteServiceOrderUseCase;
import afsj.efm.service_order.application.service_order.usecases.GetServiceOrderByIdUseCase;
import afsj.efm.service_order.application.service_order.usecases.ListServiceOrdersUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/serviceorder")
public class ServiceOrderController {

   private final ListServiceOrdersUseCase listServiceOrdersUseCase;
   private final GetServiceOrderByIdUseCase getServiceOrderByIdUseCase;
   private final CreateServiceOrderUseCase createServiceOrderUseCase;
   private final DeleteServiceOrderUseCase deleteServiceOrderUseCase;

   public ServiceOrderController(
           ListServiceOrdersUseCase listServiceOrdersUseCase,
           GetServiceOrderByIdUseCase getServiceOrderByIdUseCase,
           CreateServiceOrderUseCase createServiceOrderUseCase,
           DeleteServiceOrderUseCase deleteServiceOrderUseCase
   ) {
      this.listServiceOrdersUseCase = listServiceOrdersUseCase;
      this.getServiceOrderByIdUseCase = getServiceOrderByIdUseCase;
      this.createServiceOrderUseCase = createServiceOrderUseCase;
      this.deleteServiceOrderUseCase = deleteServiceOrderUseCase;
   }

   @GetMapping
   public ResponseEntity<List<ServiceOrderResponse>> list() {
      return ResponseEntity.ok(listServiceOrdersUseCase.execute());
   }

   @GetMapping("/{id}")
   public ResponseEntity<ServiceOrderResponse> findById(@PathVariable Long id) {
      return ResponseEntity.ok(getServiceOrderByIdUseCase.execute(id));
   }

   @PostMapping
   public ResponseEntity<ServiceOrderResponse> save(@RequestBody @Valid ServiceOrderRequest request) {
      return ResponseEntity.status(HttpStatus.CREATED).body(createServiceOrderUseCase.execute(request));
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Void> delete(@PathVariable Long id) {
      deleteServiceOrderUseCase.execute(id);
      return ResponseEntity.noContent().build();
   }
}
