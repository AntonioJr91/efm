package afsj.efm.service_order.infrastructure.web;

import afsj.efm.service_order.application.farm_area.dtos.FarmAreaRequest;
import afsj.efm.service_order.application.farm_area.dtos.FarmAreaResponse;
import afsj.efm.service_order.application.farm_area.usecases.CreateFarmAreaUseCase;
import afsj.efm.service_order.application.farm_area.usecases.DeleteFarmAreaUseCase;
import afsj.efm.service_order.application.farm_area.usecases.GetFarmAreaByIdUseCase;
import afsj.efm.service_order.application.farm_area.usecases.ListFarmAreaUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FarmAreaController.class)
class FarmAreaControllerTest {

   @Autowired
   private MockMvc mockMvc;

   @Autowired
   private ObjectMapper objectMapper;

   @MockitoBean
   private ListFarmAreaUseCase listFarmAreaUseCase;

   @MockitoBean
   private GetFarmAreaByIdUseCase getFarmAreaByIdUseCase;

   @MockitoBean
   private CreateFarmAreaUseCase createFarmAreaUseCase;

   @MockitoBean
   private DeleteFarmAreaUseCase deleteFarmAreaUseCase;

   @Test
   @DisplayName("Should return all farm areas with HTTP 200")
   void shouldListAllFarmAreas() throws Exception {

      FarmAreaResponse response1 = new FarmAreaResponse(1L,"area 51");
      FarmAreaResponse response2 = new FarmAreaResponse(2L, "area 52");

      Mockito.when(listFarmAreaUseCase.execute())
              .thenReturn(List.of(response1, response2));

      mockMvc.perform(get("/farmarea"))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.size()", is(2)))
              .andExpect(jsonPath("$[0].id", is(1)))
              .andExpect(jsonPath("$[0].name", is("area 51")));

      Mockito.verify(listFarmAreaUseCase).execute();
   }

   @Test
   @DisplayName("Should return farm area by id when it exists")
   void shouldReturnFarmAreaById() throws Exception {

      FarmAreaResponse response = new FarmAreaResponse(1L, "Area Teste");

      Mockito.when(getFarmAreaByIdUseCase.execute(1L))
              .thenReturn(response);

      mockMvc.perform(get("/farmarea/1"))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.id", is(1)))
              .andExpect(jsonPath("$.name", is("Area Teste")));

      Mockito.verify(getFarmAreaByIdUseCase).execute(1L);
   }

   @Test
   @DisplayName("Should return internal server error when farm area is not found")
   void shouldReturnNotFoundWhenFarmAreaDoesNotExist() throws Exception {

      Mockito.when(getFarmAreaByIdUseCase.execute(99L))
              .thenThrow(new RuntimeException("FarmArea not found"));

      mockMvc.perform(get("/farmarea/99"))
              .andExpect(status().isInternalServerError());

      Mockito.verify(getFarmAreaByIdUseCase).execute(99L);
   }

   @Test
   @DisplayName("Should create farm area successfully and return HTTP 201")
   void shouldCreateFarmArea() throws Exception {

      FarmAreaRequest request = new FarmAreaRequest("Nova Area");
      FarmAreaResponse response = new FarmAreaResponse(10L, "Nova Area");

      Mockito.when(createFarmAreaUseCase.execute(any(FarmAreaRequest.class)))
              .thenReturn(response);

      mockMvc.perform(post("/farmarea")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(objectMapper.writeValueAsString(request)))
              .andExpect(status().isCreated())
              .andExpect(jsonPath("$.id", is(10)))
              .andExpect(jsonPath("$.name", is("Nova Area")));

      Mockito.verify(createFarmAreaUseCase).execute(any(FarmAreaRequest.class));
   }

   @Test
   @DisplayName("Should return bad request when request body contains invalid JSON")
   void shouldReturnBadRequestWhenInvalidJson() throws Exception {

      mockMvc.perform(post("/farmarea")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content("{ invalid json }"))
              .andExpect(status().isBadRequest());
   }

   @Test
   @DisplayName("Should delete farm area successfully and return HTTP 204")
   void shouldDeleteFarmArea() throws Exception {

      Mockito.doNothing().when(deleteFarmAreaUseCase).execute(1L);

      mockMvc.perform(delete("/farmarea/1"))
              .andExpect(status().isNoContent());

      Mockito.verify(deleteFarmAreaUseCase).execute(1L);
   }

   @Test
   @DisplayName("Should return internal server error when deleting non existing farm area")
   void shouldReturnErrorWhenDeletingNonExisting() throws Exception {

      Mockito.doThrow(new RuntimeException("Not found"))
              .when(deleteFarmAreaUseCase).execute(99L);

      mockMvc.perform(delete("/farmarea/99"))
              .andExpect(status().isInternalServerError());

      Mockito.verify(deleteFarmAreaUseCase).execute(99L);
   }
}
