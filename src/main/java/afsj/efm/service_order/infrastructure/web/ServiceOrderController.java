package afsj.efm.service_order.infrastructure.web;

import afsj.efm.service_order.application.service_order.dtos.*;
import afsj.efm.service_order.application.service_order.usecases.*;
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
   private final AddItemToServiceOrderUseCase addItemToServiceOrderUseCase;
   private final ListDetailsServiceOrdersUseCase listDetailsServiceOrdersUseCase;

   public ServiceOrderController(
           ListServiceOrdersUseCase listServiceOrdersUseCase,
           GetServiceOrderByIdUseCase getServiceOrderByIdUseCase,
           CreateServiceOrderUseCase createServiceOrderUseCase,
           DeleteServiceOrderUseCase deleteServiceOrderUseCase,
           AddItemToServiceOrderUseCase addItemToServiceOrderUseCase,
           ListDetailsServiceOrdersUseCase listDetailsServiceOrdersUseCase
   ) {
      this.listServiceOrdersUseCase = listServiceOrdersUseCase;
      this.getServiceOrderByIdUseCase = getServiceOrderByIdUseCase;
      this.createServiceOrderUseCase = createServiceOrderUseCase;
      this.deleteServiceOrderUseCase = deleteServiceOrderUseCase;
      this.addItemToServiceOrderUseCase = addItemToServiceOrderUseCase;
      this.listDetailsServiceOrdersUseCase = listDetailsServiceOrdersUseCase;
   }

   @GetMapping
   public ResponseEntity<List<ServiceOrderResponse>> list() {
      return ResponseEntity.ok(listServiceOrdersUseCase.execute());
   }

   @GetMapping("/list-details")
   public ResponseEntity<List<ServiceOrderDetailResponse>> listDetails() {
      return ResponseEntity.ok(listDetailsServiceOrdersUseCase.execute());
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

   @PostMapping("/{orderId}/add-item")
   public ResponseEntity<ServiceOrderDetailResponse> addItem(@RequestBody AddItemToServiceOrderRequest request) {
      return ResponseEntity.ok(addItemToServiceOrderUseCase.execute(request));
   }
}
