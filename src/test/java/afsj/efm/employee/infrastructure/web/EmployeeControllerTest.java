package afsj.efm.employee.infrastructure.web;

import afsj.efm.employee.application.dtos.EmployeeRequest;
import afsj.efm.employee.application.dtos.EmployeeResponse;
import afsj.efm.employee.application.dtos.EmployeeUpdateRequest;
import afsj.efm.employee.application.dtos.EmployeeUpdateResponse;
import afsj.efm.employee.application.usecases.*;
import afsj.efm.employee.domain.enums.ContractType;
import afsj.efm.employee.domain.enums.JobRole;
import afsj.efm.employee.domain.enums.Status;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

   @Autowired
   private MockMvc mockMvc;

   @Autowired
   private ObjectMapper objectMapper;

   @MockitoBean
   private ListEmployeesUseCase listEmployeesUseCase;

   @MockitoBean
   private GetEmployeeByIdUseCase getEmployeeByIdUseCase;

   @MockitoBean
   private CreateEmployeeUseCase createEmployeeUseCase;

   @MockitoBean
   private UpdateEmployeeUseCase updateEmployeeUseCase;

   @MockitoBean
   private DeleteEmployeeUseCase deleteEmployeeUseCase;

   @Test
   @DisplayName("GET /employees/{id} should return employee")
   void shouldGetEmployeeById() throws Exception {

      var response = new EmployeeResponse(
              1L,
              "Antonio",
              "Sousa",
              "39053344705",
              "11999999999",
              JobRole.WORKER,
              ContractType.CLT,
              LocalDate.of(2025, 1, 1),
              Status.ACTIVE,
              LocalDate.of(2025, 1, 10));

      Mockito.when(getEmployeeByIdUseCase.execute(1L)).thenReturn(response);

      mockMvc.perform(get("/employees/{id}", 1L))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.phoneNumber").value("11999999999"));
   }

   @Test
   @DisplayName("POST /employees should create employee without phone number")
   void shouldCreateEmployeeWithoutPhoneNumber() throws Exception {

      var request = new EmployeeRequest(
              "Antonio", "Sousa", "39053344705", null,
              JobRole.ADMINISTRATOR, ContractType.CLT
      );

      var response = new EmployeeResponse(
              1L,
              "Antonio",
              "Sousa",
              "39053344705",
              null,
              JobRole.WORKER,
              ContractType.CLT,
              LocalDate.of(2025, 1, 1),
              Status.ACTIVE,
              null);

      Mockito.when(createEmployeeUseCase.execute(Mockito.any()))
              .thenReturn(response);

      mockMvc.perform(post("/employees")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(objectMapper.writeValueAsString(request)))
              .andExpect(status().isCreated())
              .andExpect(header().exists("Location"))
              .andExpect(jsonPath("$.phoneNumber").doesNotExist());
   }

   @Test
   @DisplayName("PATCH /employees/{id}/phonenumber should update phone number")
   void shouldUpdatePhoneNumber() throws Exception {

      var request = new EmployeeUpdateRequest("11999999999");
      var response = new EmployeeUpdateResponse("11999999999");

      Mockito.when(updateEmployeeUseCase.execute(1L, request))
              .thenReturn(response);

      mockMvc.perform(patch("/employees/{id}/phonenumber", 1L)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(objectMapper.writeValueAsString(request)))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.phoneNumber").value("11999999999"));
   }

   @Test
   @DisplayName("DELETE /employees/{id} should return 204")
   void shouldDeleteEmployee() throws Exception {

      mockMvc.perform(delete("/employees/{id}", 1L))
              .andExpect(status().isNoContent());

      Mockito.verify(deleteEmployeeUseCase).execute(1L);
   }
}