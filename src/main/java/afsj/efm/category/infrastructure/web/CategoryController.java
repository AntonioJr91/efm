package afsj.efm.category.infrastructure.web;

import afsj.efm.category.application.dtos.CategoryRequest;
import afsj.efm.category.application.dtos.CategoryResponse;
import afsj.efm.category.application.usecases.CreateCategoryUseCase;
import afsj.efm.category.application.usecases.DeleteCategoryUseCase;
import afsj.efm.category.application.usecases.GetCategoryByIdUseCase;
import afsj.efm.category.application.usecases.ListCategoriesUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

   private final CreateCategoryUseCase createCategoryUseCase;
   private final ListCategoriesUseCase listCategories;
   private final GetCategoryByIdUseCase getCategoryByIdUseCase;
   private final DeleteCategoryUseCase deleteCategoryUseCase;

   public CategoryController(CreateCategoryUseCase createCategoryUseCase, ListCategoriesUseCase listCategories, GetCategoryByIdUseCase getCategoryByIdUseCase, DeleteCategoryUseCase deleteCategoryUseCase) {
      this.createCategoryUseCase = createCategoryUseCase;
      this.listCategories = listCategories;
      this.getCategoryByIdUseCase = getCategoryByIdUseCase;
      this.deleteCategoryUseCase = deleteCategoryUseCase;
   }

   @GetMapping
   public ResponseEntity<List<CategoryResponse>> listCategories() {
      return ResponseEntity.ok().body(listCategories.execute());
   }

   @GetMapping("/{id}")
   public ResponseEntity<CategoryResponse> findById(@PathVariable Long id) {
      return ResponseEntity.ok(getCategoryByIdUseCase.execute(id));
   }

   @PostMapping
   public ResponseEntity<CategoryResponse> save(@RequestBody @Valid CategoryRequest request) {
      var newCategory = createCategoryUseCase.execute(request);

      URI location = ServletUriComponentsBuilder
              .fromCurrentRequest()
              .path("/{id}")
              .buildAndExpand(newCategory.id())
              .toUri();

      return ResponseEntity.created(location).body(newCategory);
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Void> delete(@PathVariable Long id) {
      deleteCategoryUseCase.execute(id);
      return ResponseEntity.noContent().build();
   }
}
