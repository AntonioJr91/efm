package afsj.efm.production.infraestructure.web;

import afsj.efm.production.application.dtos.ProductionRequest;
import afsj.efm.production.application.dtos.ProductionResponse;
import afsj.efm.production.application.usecases.CreateProductionUseCase;
import afsj.efm.production.application.usecases.GetProductionByIdUseCase;
import afsj.efm.production.application.usecases.ListProductionsUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productions")
public class ProductionController {

   private final ListProductionsUseCase listProductionsUseCase;
   private final GetProductionByIdUseCase getProductionByIdUseCase;
   private final CreateProductionUseCase createProductionUseCase;

   public ProductionController(
           ListProductionsUseCase listProductionsUseCase,
           GetProductionByIdUseCase getProductionByIdUseCase,
           CreateProductionUseCase createProductionUseCase
   ) {
      this.listProductionsUseCase = listProductionsUseCase;
      this.getProductionByIdUseCase = getProductionByIdUseCase;
      this.createProductionUseCase = createProductionUseCase;
   }

   @GetMapping
   public ResponseEntity<List<ProductionResponse>> findAll() {
      return ResponseEntity.ok(listProductionsUseCase.execute());
   }

   @GetMapping("/{id}")
   public ResponseEntity<ProductionResponse> findById(@PathVariable Long id) {
      return ResponseEntity.ok(getProductionByIdUseCase.execute(id));
   }

   @PostMapping
   public ResponseEntity<ProductionResponse> create(@RequestBody @Valid ProductionRequest request) {
      return ResponseEntity.status(HttpStatus.CREATED).body(createProductionUseCase.execute(request));
   }
}
