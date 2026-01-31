package afsj.efm.product.infrastructure.web;

import afsj.efm.category.application.usecases.DeleteCategoryUseCase;
import afsj.efm.product.application.dtos.ProductResponse;
import afsj.efm.product.application.dtos.StockUpdateResponse;
import afsj.efm.product.application.errors.ProductConflicts;
import afsj.efm.product.application.errors.ProductNotFound;
import afsj.efm.product.application.usecases.*;
import afsj.efm.product.domain.enums.UnitOfMeasure;
import afsj.efm.product.domain.exceptions.InsufficientStockException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

   @MockitoBean
   private ListProductsUseCase listProducts;
   @MockitoBean
   private GetProductByIdUseCase getProductByIdUseCase;
   @MockitoBean
   private CreateProductUseCase createProductUseCase;
   @MockitoBean
   private DeleteCategoryUseCase deleteCategoryUseCase;
   @MockitoBean
   private IncreaseStockProductUseCase increaseStockProductUseCase;
   @MockitoBean
   private DecreaseStockProductUseCase decreaseStockProductUseCase;

   @Autowired
   private MockMvc mockMvc;

   @Test
   @DisplayName("Should return 200 when listing products")
   void return200ListingProducts() throws Exception {
      mockMvc.perform(get("/products"))
              .andExpect(status().isOk());
   }

   @Test
   @DisplayName("Should return 200 when find product by id")
   void return200FindById() throws Exception {
      Mockito.when(getProductByIdUseCase.execute(1L))
              .thenReturn(new ProductResponse(
                      1L,
                      "milho",
                      10,
                      UnitOfMeasure.UNIT,
                      LocalDate.now(),
                      1L
              ));

      mockMvc.perform(get("/products/1"))
              .andExpect(status().isOk());
   }

   @Test
   @DisplayName("Should return 404 when product is not found by id")
   void return404WhenProductNotFound() throws Exception {
      Mockito.when(getProductByIdUseCase.execute(1L))
              .thenThrow(ProductNotFound.byId(1L));

      mockMvc.perform(get("/products/1"))
              .andExpect(status().isNotFound());
   }

   @Test
   @DisplayName("Should return 201 when creating product")
   void return201WhenCreatingProduct() throws Exception {
      Mockito.when(createProductUseCase.execute(Mockito.any()))
              .thenReturn(new ProductResponse(
                      1L,
                      "milho",
                      10,
                      UnitOfMeasure.UNIT,
                      LocalDate.now(),
                      1L
              ));

      mockMvc.perform(post("/products")
                      .contentType("application/json")
                      .content("""
                                  {
                                    "name": "milho",
                                    "stock": 10,
                                    "unitOfMeasure": "UNIT",
                                       "categoryId": 1
                                  }
                              """))
              .andExpect(status().isCreated());
   }

   @Test
   @DisplayName("Should return 409 when product name already exists")
   void return409WhenDuplicateName() throws Exception {
      Mockito.when(createProductUseCase.execute(Mockito.any()))
              .thenThrow(ProductConflicts.nameAlreadyExists("milho"));

      mockMvc.perform(post("/products")
                      .contentType("application/json")
                      .content("""
                                  {
                                    "name": "milho",
                                    "stock": 10,
                                    "unitOfMeasure": "UNIT",
                                    "categoryId": 1
                                  }
                              """))
              .andExpect(status().isConflict());
   }

   @Test
   @DisplayName("Should return 400 when product request body is invalid")
   void return400WhenInvalidProductRequest() throws Exception {
      mockMvc.perform(post("/products")
                      .contentType("application/json")
                      .content("""
                                  {
                                    "name": "",
                                    "stock": -1,
                                    "unitOfMeasure": null
                                  }
                              """))
              .andExpect(status().isBadRequest());
   }

   @Test
   @DisplayName("Should return 200 when increasing product stock")
   void return200WhenIncreaseStock() throws Exception {
      Mockito.when(increaseStockProductUseCase.execute(Mockito.eq(1L), Mockito.anyInt()))
              .thenReturn(new StockUpdateResponse(1L, "semente", 15));

      mockMvc.perform(patch("/products/1/increase")
                      .contentType("application/json")
                      .content("""
                                  { "quantity": 5 }
                              """))
              .andExpect(status().isOk());
   }

   @Test
   @DisplayName("Should return 200 when decreasing product stock")
   void return200WhenDecreaseStock() throws Exception {
      Mockito.when(decreaseStockProductUseCase.execute(Mockito.eq(1L), Mockito.anyInt()))
              .thenReturn(new StockUpdateResponse(1L, "semente", 10));

      mockMvc.perform(patch("/products/1/decrease")
                      .contentType("application/json")
                      .content("""
                                  { "quantity": 5 }
                              """))
              .andExpect(status().isOk());
   }

   @Test
   @DisplayName("Should return 400 when increase stock request is invalid")
   void return400WhenInvalidIncreaseRequest() throws Exception {
      mockMvc.perform(patch("/products/1/increase")
                      .contentType("application/json")
                      .content("""
                                  { "quantity": 0 }
                              """))
              .andExpect(status().isBadRequest());
   }

   @Test
   @DisplayName("Should return 400 when decrease stock request is invalid")
   void return400WhenInvalidDecreaseRequest() throws Exception {
      mockMvc.perform(patch("/products/1/decrease")
                      .contentType("application/json")
                      .content("""
                                  { "quantity": 0 }
                              """))
              .andExpect(status().isBadRequest());
   }

   @Test
   @DisplayName("Should return 204 when deleting existing product")
   void return204WhenDeleteProduct() throws Exception {
      Mockito.doNothing().when(deleteCategoryUseCase).execute(1L);

      mockMvc.perform(delete("/products/1"))
              .andExpect(status().isNoContent());
   }

   @Test
   @DisplayName("Should return 404 when deleting non existing product")
   void return404WhenDeleteProductNotFound() throws Exception {
      Mockito.doThrow(ProductNotFound.byId(1L))
              .when(deleteCategoryUseCase).execute(1L);

      mockMvc.perform(delete("/products/1"))
              .andExpect(status().isNotFound());
   }

   @Test
   @DisplayName("Should return 400 when decreasing stock below available")
   void return400WhenInsufficientStock() throws Exception {
      Mockito.when(decreaseStockProductUseCase.execute(Mockito.eq(1L), Mockito.anyInt()))
              .thenThrow(new InsufficientStockException("error"));

      mockMvc.perform(patch("/products/1/decrease")
                      .contentType("application/json")
                      .content("{\"quantity\": 5}"))
              .andExpect(status().isBadRequest());
   }
}
