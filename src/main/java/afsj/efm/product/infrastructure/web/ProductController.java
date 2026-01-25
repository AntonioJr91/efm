package afsj.efm.product.infrastructure.web;

import afsj.efm.product.application.dtos.*;
import afsj.efm.product.application.service.ProductApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

   private final ProductApplicationService service;

   public ProductController(ProductApplicationService service) {
      this.service = service;
   }

   @GetMapping
   public ResponseEntity<List<ProductResponse>> listProducts() {
      return ResponseEntity.ok(service.listProducts());
   }

   @GetMapping("/{id}")
   public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
      return ResponseEntity.ok(service.findById(id));
   }

   @PostMapping
   public ResponseEntity<ProductResponse> save(@RequestBody @Valid ProductRequest request) {
      ProductResponse newProduct = service.save(request);

      URI location = ServletUriComponentsBuilder
              .fromCurrentRequest()
              .path("/{id}")
              .buildAndExpand(newProduct.id())
              .toUri();

      return ResponseEntity.created(location).body(newProduct);
   }

   @PatchMapping("/{id}/increase")
   public ResponseEntity<StockUpdateResponse> increaseStock(@PathVariable Long id,
                                                            @RequestBody @Valid IncreaseStockRequest request) {
      StockUpdateResponse updated = service.increase(id, request.quantity());
      return ResponseEntity.ok().body(updated);
   }

   @PatchMapping("/{id}/decrease")
   public ResponseEntity<StockUpdateResponse> decreaseStock(@PathVariable Long id,
                                                            @RequestBody @Valid DecreaseStockRequest request) {
      StockUpdateResponse updated = service.decrease(id, request.quantity());
      return ResponseEntity.ok().body(updated);
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Void> delete(@PathVariable Long id) {
      service.delete(id);
      return ResponseEntity.noContent().build();
   }
}
