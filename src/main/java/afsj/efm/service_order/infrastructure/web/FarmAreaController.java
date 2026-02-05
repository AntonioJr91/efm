package afsj.efm.service_order.infrastructure.web;

import afsj.efm.service_order.application.farm_area.dtos.FarmAreaRequest;
import afsj.efm.service_order.application.farm_area.dtos.FarmAreaResponse;
import afsj.efm.service_order.application.farm_area.usecases.CreateFarmAreaUseCase;
import afsj.efm.service_order.application.farm_area.usecases.DeleteFarmAreaUseCase;
import afsj.efm.service_order.application.farm_area.usecases.GetFarmAreaByIdUseCase;
import afsj.efm.service_order.application.farm_area.usecases.ListFarmAreaUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/farmarea")
public class FarmAreaController {

   private final ListFarmAreaUseCase listFarmAreaUseCase;
   private final GetFarmAreaByIdUseCase getFarmAreaByIdUseCase;
   private final CreateFarmAreaUseCase createFarmAreaUseCase;
   private final DeleteFarmAreaUseCase deleteFarmAreaUseCase;

   public FarmAreaController(
           ListFarmAreaUseCase listFarmAreaUseCase,
           GetFarmAreaByIdUseCase getFarmAreaByIdUseCase,
           CreateFarmAreaUseCase createFarmAreaUseCase,
           DeleteFarmAreaUseCase deleteFarmAreaUseCase
   ) {
      this.listFarmAreaUseCase = listFarmAreaUseCase;
      this.getFarmAreaByIdUseCase = getFarmAreaByIdUseCase;
      this.createFarmAreaUseCase = createFarmAreaUseCase;
      this.deleteFarmAreaUseCase = deleteFarmAreaUseCase;
   }

   @GetMapping
   public ResponseEntity<List<FarmAreaResponse>> list() {
      return ResponseEntity.ok(listFarmAreaUseCase.execute());
   }

   @GetMapping("/{id}")
   public ResponseEntity<FarmAreaResponse> findById(@PathVariable Long id) {
      return ResponseEntity.ok(getFarmAreaByIdUseCase.execute(id));
   }

   @PostMapping
   public ResponseEntity<FarmAreaResponse> save(@RequestBody FarmAreaRequest request) {
      return ResponseEntity.status(HttpStatus.CREATED).body(createFarmAreaUseCase.execute(request));
   }

   @DeleteMapping
   public ResponseEntity<Void> delete(@PathVariable Long id) {
      deleteFarmAreaUseCase.execute(id);
      return ResponseEntity.noContent().build();
   }
}
