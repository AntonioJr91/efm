package afsj.efm.production.infraestructure.web;

import afsj.efm.production.application.dtos.ProductionResponse;
import afsj.efm.production.application.errors.ProductionNotFound;
import afsj.efm.production.application.usecases.CreateProductionUseCase;
import afsj.efm.production.application.usecases.GetProductionByIdUseCase;
import afsj.efm.production.application.usecases.ListProductionsUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductionController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductionControllerTest {

   @MockitoBean
   private ListProductionsUseCase listProductionsUseCase;

   @MockitoBean
   private GetProductionByIdUseCase getProductionByIdUseCase;

   @MockitoBean
   private CreateProductionUseCase createProductionUseCase;

   @Autowired
   private MockMvc mockMvc;

   @Test
   @DisplayName("Should return 200 when listing productions")
   void shouldReturn200WhenListingProductions() throws Exception {
      when(listProductionsUseCase.execute()).thenReturn(List.of());

      mockMvc.perform(get("/productions"))
              .andExpect(status().isOk());
   }

   @Test
   @DisplayName("Should return 200 when finding production by id")
   void shouldReturn200WhenFindingProductionById() throws Exception {
      var response = new ProductionResponse(1L, 1L, 1L, 100, "observation", LocalDate.now());

      when(getProductionByIdUseCase.execute(1L)).thenReturn(response);

      mockMvc.perform(get("/productions/1"))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.id").value(1))
              .andExpect(jsonPath("$.areaId").value(1))
              .andExpect(jsonPath("$.productId").value(1))
              .andExpect(jsonPath("$.quantity").value(100))
              .andExpect(jsonPath("$.observation").value("observation"));
   }

   @Test
   @DisplayName("Should return 404 when production is not found by id")
   void shouldReturn404WhenProductionIsNotFoundById() throws Exception {
      when(getProductionByIdUseCase.execute(1L))
              .thenThrow(ProductionNotFound.byId());

      mockMvc.perform(get("/productions/1"))
              .andExpect(status().isNotFound());
   }

   @Test
   @DisplayName("Should return 201 when creating production")
   void shouldReturn201WhenCreatingProduction() throws Exception {
      var request = """
              {
                "areaId": 1,
                "productId": 1,
                "quantity": 100,
                "observation": "observation"
              }
              """;

      var response = new ProductionResponse(
              1L,
              1L,
              1L,
              100,
              "observation",
              LocalDate.of(2024, 6, 1)
      );

      when(createProductionUseCase.execute(any()))
              .thenReturn(response);

      mockMvc.perform(post("/productions")
                      .contentType("application/json")
                      .content(request))
              .andExpect(status().isCreated())
              .andExpect(jsonPath("$.id").value(1))
              .andExpect(jsonPath("$.areaId").value(1))
              .andExpect(jsonPath("$.productId").value(1))
              .andExpect(jsonPath("$.quantity").value(100))
              .andExpect(jsonPath("$.observation").value("observation"));
   }

   @Test
   @DisplayName("Should return 400 when creating production with invalid data")
   void shouldReturn400WhenCreatingProductionWithInvalidData() throws Exception {
      var request = """
              {
                "areaId": null,
                "productId": null,
                "quantity": null,
                "observation": "observation"
              }
              """;

      mockMvc.perform(post("/productions")
                      .contentType("application/json")
                      .content(request))
              .andExpect(status().isBadRequest());
   }
}