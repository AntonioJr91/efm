package afsj.efm.category.infrastructure.web;

import afsj.efm.category.application.dtos.CategoryRequest;
import afsj.efm.category.application.dtos.CategoryResponse;
import afsj.efm.category.application.services.CategoryApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

   private final CategoryApplicationService service;

   public CategoryController(CategoryApplicationService service) {
      this.service = service;
   }

   @GetMapping
   public ResponseEntity<List<CategoryResponse>> listCategories() {
      return ResponseEntity.ok().body(service.listCategories());
   }

   @GetMapping("/{id}")
   public ResponseEntity<CategoryResponse> findById(@PathVariable Long id) {
      return ResponseEntity.ok(service.findById(id));
   }

   @PostMapping
   public ResponseEntity<CategoryResponse> save(@RequestBody @Valid CategoryRequest request) {
      var newCategory = service.save(request);

      URI location = ServletUriComponentsBuilder
              .fromCurrentRequest()
              .path("/{id}")
              .buildAndExpand(newCategory.id())
              .toUri();

      return ResponseEntity.created(location).body(newCategory);
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Void> delete(@PathVariable Long id) {
      service.delete(id);
      return ResponseEntity.noContent().build();
   }
}
