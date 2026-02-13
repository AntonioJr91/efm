package afsj.efm.product.infrastructure.web;

import afsj.efm.product.application.dtos.*;
import afsj.efm.product.application.usecases.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

   private final ListProductsUseCase listProducts;
   private final GetProductByIdUseCase getProductByIdUseCase;
   private final CreateProductUseCase createProductUseCase;
   private final DeleteProductUseCase deleteProductUseCase;
   private final IncreaseStockProductUseCase increaseStockProductUseCase;
   private final DecreaseStockProductUseCase decreaseStockProductUseCase;

   public ProductController(
           ListProductsUseCase listProducts,
           GetProductByIdUseCase getProductByIdUseCase,
           CreateProductUseCase createProductUseCase,
           DeleteProductUseCase deleteProductUseCase,
           IncreaseStockProductUseCase increaseStockProductUseCase,
           DecreaseStockProductUseCase decreaseStockProductUseCase
   ) {
      this.listProducts = listProducts;
      this.getProductByIdUseCase = getProductByIdUseCase;
      this.createProductUseCase = createProductUseCase;
      this.deleteProductUseCase = deleteProductUseCase;
      this.increaseStockProductUseCase = increaseStockProductUseCase;
      this.decreaseStockProductUseCase = decreaseStockProductUseCase;
   }


   @GetMapping
   public ResponseEntity<List<ProductResponse>> listProducts() {
      return ResponseEntity.ok(listProducts.execute());
   }

   @GetMapping("/{id}")
   public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
      return ResponseEntity.ok(getProductByIdUseCase.execute(id));
   }

   @PostMapping
   public ResponseEntity<ProductResponse> save(@RequestBody @Valid ProductRequest request) {
      ProductResponse newProduct = createProductUseCase.execute(request);

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
      StockUpdateResponse updated = increaseStockProductUseCase.execute(id, request.quantity());
      return ResponseEntity.ok().body(updated);
   }

   @PatchMapping("/{id}/decrease")
   public ResponseEntity<StockUpdateResponse> decreaseStock(@PathVariable Long id,
                                                            @RequestBody @Valid DecreaseStockRequest request) {
      StockUpdateResponse updated = decreaseStockProductUseCase.execute(id, request.quantity());
      return ResponseEntity.ok().body(updated);
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Void> delete(@PathVariable Long id) {
      deleteProductUseCase.execute(id);
      return ResponseEntity.noContent().build();
   }
}
