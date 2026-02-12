package afsj.efm.service_order.infrastructure.web;

import afsj.efm.employee.domain.enums.JobRole;
import afsj.efm.service_order.application.service_order.dtos.*;
import afsj.efm.service_order.application.service_order.usecases.*;
import afsj.efm.service_order.domain.enums.ServiceCategory;
import afsj.efm.service_order.domain.enums.StatusOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ServiceOrderController.class)
class ServiceOrderControllerTest {

   @Autowired
   private MockMvc mockMvc;

   @Autowired
   private ObjectMapper objectMapper;

   @MockitoBean
   private ListServiceOrdersUseCase listServiceOrdersUseCase;
   @MockitoBean
   private GetServiceOrderByIdUseCase getServiceOrderByIdUseCase;
   @MockitoBean
   private CreateServiceOrderUseCase createServiceOrderUseCase;
   @MockitoBean
   private DeleteServiceOrderUseCase deleteServiceOrderUseCase;
   @MockitoBean
   private AddItemToServiceOrderUseCase addItemToServiceOrderUseCase;
   @MockitoBean
   private ListDetailsServiceOrdersUseCase listDetailsServiceOrdersUseCase;
   @MockitoBean
   private IncreaseServiceOrderItemUseCase increaseServiceOrderItemUseCase;
   @MockitoBean
   private DecreaseServiceOrderItemUseCase decreaseServiceOrderItemUseCase;
   @MockitoBean
   private CompleteOrderServiceUseCase completeOrderServiceUseCase;
   @MockitoBean
   private CancelOrderServiceUseCase cancelOrderServiceUseCase;

   @Test
   @DisplayName("Should return all service orders with HTTP 200")
   void shouldReturnAllServiceOrdersWhenListing() throws Exception {

      ServiceOrderResponse response = new ServiceOrderResponse(
              1L,
              "Xibatinha",
              "area 51",
              "plantar",
              StatusOrder.IN_PROGRESS.name(),
              LocalDate.now()
      );

      Mockito.when(listServiceOrdersUseCase.execute())
              .thenReturn(List.of(response));

      mockMvc.perform(get("/serviceorder"))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$[0].id", is(1)));

      Mockito.verify(listServiceOrdersUseCase).execute();
   }

   @Test
   @DisplayName("Should return detailed service orders with HTTP 200")
   void shouldReturnDetailedServiceOrders() throws Exception {

      ServiceOrderDetailResponse detail = new ServiceOrderDetailResponse(
              1L,
              "OPEN",
              JobRole.WORKER.name(),
              "area 51",
              "plantar",
              "descrição",
              ServiceCategory.PLANTING.name(),
              StatusOrder.IN_PROGRESS.name(),
              LocalDate.now(),
              LocalDate.now().plusDays(1),
              List.of()
      );

      Mockito.when(listDetailsServiceOrdersUseCase.execute())
              .thenReturn(List.of(detail));

      mockMvc.perform(get("/serviceorder/list-details"))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$[0].id", is(1)));

      Mockito.verify(listDetailsServiceOrdersUseCase).execute();
   }

   @Test
   @DisplayName("Should return service order by id when exists")
   void shouldReturnServiceOrderByIdWhenExists() throws Exception {

      ServiceOrderResponse response = new ServiceOrderResponse(
              1L,
              "Xibatinha",
              "area 51",
              "plantar",
              StatusOrder.IN_PROGRESS.name(),
              LocalDate.now()
      );

      Mockito.when(getServiceOrderByIdUseCase.execute(1L))
              .thenReturn(response);

      mockMvc.perform(get("/serviceorder/1"))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.id", is(1)));

      Mockito.verify(getServiceOrderByIdUseCase).execute(1L);
   }

   @Test
   @DisplayName("Should create service order and return HTTP 201")
   void shouldCreateServiceOrderSuccessfully() throws Exception {

      ServiceOrderRequest request = new ServiceOrderRequest(
              "Client A",
              "area 51",
              "plantar",
              "descrição",
              ServiceCategory.PLANTING
      );
      ServiceOrderResponse response = new ServiceOrderResponse(
              1L,
              "Xibatinha",
              "area 51",
              "plantar",
              StatusOrder.IN_PROGRESS.name(),
              LocalDate.now()
      );

      Mockito.when(createServiceOrderUseCase.execute(any(ServiceOrderRequest.class)))
              .thenReturn(response);

      mockMvc.perform(post("/serviceorder")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(objectMapper.writeValueAsString(request)))
              .andExpect(status().isCreated())
              .andExpect(jsonPath("$.id", is(1)));

      Mockito.verify(createServiceOrderUseCase).execute(any(ServiceOrderRequest.class));
   }

   @Test
   @DisplayName("Should return bad request when service order request is invalid")
   void shouldReturnBadRequestWhenInvalidRequest() throws Exception {

      mockMvc.perform(post("/serviceorder")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content("{}"))
              .andExpect(status().isBadRequest());
   }

   @Test
   @DisplayName("Should delete service order and return HTTP 204")
   void shouldDeleteServiceOrder() throws Exception {

      Mockito.doNothing().when(deleteServiceOrderUseCase).execute(1L);

      mockMvc.perform(delete("/serviceorder/1"))
              .andExpect(status().isNoContent());

      Mockito.verify(deleteServiceOrderUseCase).execute(1L);
   }

   @Test
   @DisplayName("Should add item to service order and return HTTP 200")
   void shouldAddItemToServiceOrder() throws Exception {

      AddItemToServiceOrderRequest request =
              new AddItemToServiceOrderRequest(1L, 1L, 5);

      ServiceOrderDetailResponse response =
              new ServiceOrderDetailResponse(
                      1L,
                      "OPEN",
                      JobRole.WORKER.name(),
                      "area 51",
                      "plantar",
                      "descrição",
                      ServiceCategory.PLANTING.name(),
                      StatusOrder.IN_PROGRESS.name(),
                      LocalDate.now(),
                      LocalDate.now().plusDays(1),
                      List.of()
              );

      Mockito.when(addItemToServiceOrderUseCase.execute(any()))
              .thenReturn(response);

      mockMvc.perform(post("/serviceorder/add-item")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(objectMapper.writeValueAsString(request)))
              .andExpect(status().isOk());

      Mockito.verify(addItemToServiceOrderUseCase).execute(any());
   }

   @Test
   @DisplayName("Should increase item quantity and return HTTP 200")
   void shouldIncreaseItemQuantity() throws Exception {

      UUID itemId = UUID.randomUUID();

      ChangeItemQuantityRequest request = new ChangeItemQuantityRequest(2);

      ServiceOrderDetailResponse response =
              new ServiceOrderDetailResponse(
                      1L,
                      "OPEN",
                      JobRole.WORKER.name(),
                      "area 51",
                      "plantar",
                      "descrição",
                      ServiceCategory.PLANTING.name(),
                      StatusOrder.IN_PROGRESS.name(),
                      LocalDate.now(),
                      LocalDate.now().plusDays(1),
                      List.of()
              );

      Mockito.when(increaseServiceOrderItemUseCase.execute(eq(1L), eq(itemId), eq(2)))
              .thenReturn(response);

      mockMvc.perform(patch("/serviceorder/1/items/" + itemId + "/increase")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(objectMapper.writeValueAsString(request)))
              .andExpect(status().isOk());

      Mockito.verify(increaseServiceOrderItemUseCase)
              .execute(1L, itemId, 2);
   }

   @Test
   @DisplayName("Should decrease item quantity and return HTTP 200")
   void shouldDecreaseItemQuantity() throws Exception {

      UUID itemId = UUID.randomUUID();

      ChangeItemQuantityRequest request = new ChangeItemQuantityRequest(1);

      ServiceOrderDetailResponse response =
              new ServiceOrderDetailResponse(
                      1L,
                      "OPEN",
                      JobRole.WORKER.name(),
                      "area 51",
                      "plantar",
                      "descrição",
                      ServiceCategory.PLANTING.name(),
                      StatusOrder.IN_PROGRESS.name(),
                      LocalDate.now(),
                      LocalDate.now().plusDays(1),
                      List.of()
              );

      Mockito.when(decreaseServiceOrderItemUseCase.execute(eq(1L), eq(itemId), eq(1)))
              .thenReturn(response);

      mockMvc.perform(patch("/serviceorder/1/items/" + itemId + "/decrease")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(objectMapper.writeValueAsString(request)))
              .andExpect(status().isOk());

      Mockito.verify(decreaseServiceOrderItemUseCase)
              .execute(1L, itemId, 1);
   }

   @Test
   @DisplayName("Should complete service order and return HTTP 204")
   void shouldCompleteServiceOrder() throws Exception {

      Mockito.doNothing().when(completeOrderServiceUseCase).execute(1L);

      mockMvc.perform(patch("/serviceorder/complete/1"))
              .andExpect(status().isNoContent());

      Mockito.verify(completeOrderServiceUseCase).execute(1L);
   }

   @Test
   @DisplayName("Should cancel service order and return HTTP 204")
   void shouldCancelServiceOrder() throws Exception {

      Mockito.doNothing().when(cancelOrderServiceUseCase).execute(1L);

      mockMvc.perform(patch("/serviceorder/cancel/1"))
              .andExpect(status().isNoContent());

      Mockito.verify(cancelOrderServiceUseCase).execute(1L);
   }
}
