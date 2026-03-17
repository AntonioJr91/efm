package afsj.efm.category.infrastructure.web;

import afsj.efm.category.application.dtos.CategoryResponse;
import afsj.efm.category.application.errors.CategoryConflicts;
import afsj.efm.category.application.errors.CategoryNotFound;
import afsj.efm.category.application.usecases.CreateCategoryUseCase;
import afsj.efm.category.application.usecases.DeleteCategoryUseCase;
import afsj.efm.category.application.usecases.GetCategoryByIdUseCase;
import afsj.efm.category.application.usecases.ListCategoriesUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
class CategoryControllerTest {

   @MockitoBean
   private CreateCategoryUseCase createCategoryUseCase;
   @MockitoBean
   private ListCategoriesUseCase listCategories;
   @MockitoBean
   private GetCategoryByIdUseCase getCategoryByIdUseCase;
   @MockitoBean
   private DeleteCategoryUseCase deleteCategoryUseCase;

   @Autowired
   private MockMvc mockMvc;

   @Test
   @DisplayName("Should return 200 when listing categories")
   void return200ListingCategory() throws Exception {
      mockMvc.perform(get("/categories"))
              .andExpect(status().isOk());
   }

   @Test
   @DisplayName("Should return 200 when find category by id")
   void return200FindById() throws Exception {
      Mockito.when(getCategoryByIdUseCase.execute(1L))
              .thenReturn(new CategoryResponse(1L, "semente"));

      mockMvc.perform(get("/categories/1"))
              .andExpect(status().isOk());
   }

   @Test
   @DisplayName("Should return 404 when not found by id")
   void returnHttpStatus404() throws Exception {
      Mockito.when(getCategoryByIdUseCase.execute(1L))
              .thenThrow(CategoryNotFound.byId());

      mockMvc.perform(get("/categories/1"))
              .andExpect(status().isNotFound());
   }

   @Test
   @DisplayName("Should return 201 when creating category")
   void returnHttpStatus201() throws Exception {
      Mockito.when(createCategoryUseCase.execute(Mockito.any()))
              .thenReturn(new CategoryResponse(1L, "semente"));

      mockMvc.perform(post("/categories")
                      .contentType("application/json")
                      .content("""
                                  { "name": "semente" }
                              """))
              .andExpect(status().isCreated());
   }

   @Test
   @DisplayName("Should return 409 when category name already exists")
   void return409WhenDuplicateName() throws Exception {
      Mockito.when(createCategoryUseCase.execute(Mockito.any()))
              .thenThrow(CategoryConflicts.nameAlreadyExists());

      mockMvc.perform(post("/categories")
                      .contentType("application/json")
                      .content("""
                                  { "name": "semente" }
                              """))
              .andExpect(status().isConflict());
   }

   @Test
   @DisplayName("Should return 400 when request body is invalid")
   void return400WhenInvalidRequest() throws Exception {
      mockMvc.perform(post("/categories")
                      .contentType("application/json")
                      .content("""
                                  { "name": "" }
                              """))
              .andExpect(status().isBadRequest());
   }

   @Test
   @DisplayName("Should return 204 when deleting existing category")
   void return204WhenDelete() throws Exception {
      Mockito.doNothing().when(deleteCategoryUseCase).execute(1L);

      mockMvc.perform(delete("/categories/1"))
              .andExpect(status().isNoContent());
   }

   @Test
   @DisplayName("Should return 404 when deleting non-existing category")
   void return404WhenDeleteNotFound() throws Exception {
      Mockito.doThrow(CategoryNotFound.byId())
              .when(deleteCategoryUseCase).execute(1L);

      mockMvc.perform(delete("/categories/1"))
              .andExpect(status().isNotFound());
   }


}