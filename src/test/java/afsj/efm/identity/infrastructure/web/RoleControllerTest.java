package afsj.efm.identity.infrastructure.web;

import afsj.efm.identity.application.roles.dtos.RoleRequest;
import afsj.efm.identity.application.roles.dtos.RoleResponse;
import afsj.efm.identity.application.roles.usecases.CreateRoleUseCase;
import afsj.efm.identity.application.roles.usecases.DeleteRoleUseCase;
import afsj.efm.identity.application.roles.usecases.GetRoleByIdUseCase;
import afsj.efm.identity.application.roles.usecases.ListRolesUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoleController.class)
@AutoConfigureMockMvc(addFilters = false)
class RoleControllerTest {

   @Autowired
   private MockMvc mockMvc;

   @Autowired
   private ObjectMapper objectMapper;

   @MockitoBean
   private ListRolesUseCase listRolesUseCase;

   @MockitoBean
   private GetRoleByIdUseCase getRoleByIdUseCase;

   @MockitoBean
   private CreateRoleUseCase createRoleUseCase;

   @MockitoBean
   private DeleteRoleUseCase deleteRoleUseCase;

   @Test
   @DisplayName("GET /roles deve retornar lista de roles")
   void shouldReturnAllRoles() throws Exception {

      var response = List.of(new RoleResponse(1L, "ADMIN"));
      when(listRolesUseCase.execute()).thenReturn(response);

      mockMvc.perform(get("/roles"))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$[0].id").value(1))
              .andExpect(jsonPath("$[0].roleName").value("ADMIN"));

      verify(listRolesUseCase).execute();
   }

   @Test
   @DisplayName("GET /roles/{id} deve retornar role por id")
   void shouldReturnRoleById() throws Exception {

      when(getRoleByIdUseCase.execute(1L))
              .thenReturn(new RoleResponse(1L, "ADMIN"));

      mockMvc.perform(get("/roles/1"))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.id").value(1))
              .andExpect(jsonPath("$.roleName").value("ADMIN"));

      verify(getRoleByIdUseCase).execute(1L);
   }

   @Test
   @DisplayName("POST /roles deve criar role e retornar 201")
   void shouldCreateRole() throws Exception {

      RoleRequest request = new RoleRequest("ADMIN");
      RoleResponse response = new RoleResponse(1L, "ADMIN");

      when(createRoleUseCase.execute(any())).thenReturn(response);

      mockMvc.perform(post("/roles")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(objectMapper.writeValueAsString(request)))
              .andExpect(status().isCreated())
              .andExpect(header().exists("Location"))
              .andExpect(jsonPath("$.id").value(1))
              .andExpect(jsonPath("$.roleName").value("ADMIN"));

      verify(createRoleUseCase).execute(any());
   }

   @Test
   @DisplayName("POST /roles deve retornar 400 quando payload inválido")
   void shouldReturnBadRequestWhenInvalidPayload() throws Exception {

      RoleRequest request = new RoleRequest(""); // supondo @NotBlank no DTO

      mockMvc.perform(post("/roles")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(objectMapper.writeValueAsString(request)))
              .andExpect(status().isBadRequest());

      verifyNoInteractions(createRoleUseCase);
   }

   @Test
   @DisplayName("DELETE /roles/{id} deve retornar 204")
   void shouldDeleteRole() throws Exception {

      mockMvc.perform(delete("/roles/1"))
              .andExpect(status().isNoContent());

      verify(deleteRoleUseCase).execute(1L);
   }
}