package afsj.efm.identity.infrastructure.web;

import afsj.efm.identity.application.users.dtos.UserRequest;
import afsj.efm.identity.application.users.dtos.UserResponse;
import afsj.efm.identity.application.users.usecases.CreateUserUseCase;
import afsj.efm.identity.application.users.usecases.DeleteUserUseCase;
import afsj.efm.identity.application.users.usecases.GetUserByIdUseCase;
import afsj.efm.identity.application.users.usecases.ListUsersUseCase;
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

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

   @Autowired
   private MockMvc mockMvc;

   @Autowired
   private ObjectMapper objectMapper;

   @MockitoBean
   private ListUsersUseCase listUsersUseCase;

   @MockitoBean
   private GetUserByIdUseCase getUserByIdUseCase;

   @MockitoBean
   private CreateUserUseCase createUserUseCase;

   @MockitoBean
   private DeleteUserUseCase deleteUserUseCase;

   @Test
   @DisplayName("GET /users deve retornar lista de usuários")
   void shouldReturnAllUsers() throws Exception {

      var response = List.of(new UserResponse(1L, "antonio"));
      when(listUsersUseCase.execute()).thenReturn(response);

      mockMvc.perform(get("/users"))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$[0].id").value(1))
              .andExpect(jsonPath("$[0].username").value("antonio"));

      verify(listUsersUseCase).execute();
   }

   @Test
   @DisplayName("GET /users/{id} deve retornar usuário por id")
   void shouldReturnUserById() throws Exception {

      when(getUserByIdUseCase.execute(1L))
              .thenReturn(new UserResponse(1L, "antonio"));

      mockMvc.perform(get("/users/1"))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.id").value(1))
              .andExpect(jsonPath("$.username").value("antonio"));

      verify(getUserByIdUseCase).execute(1L);
   }

   @Test
   @DisplayName("POST /users deve criar usuário e retornar 201")
   void shouldCreateUser() throws Exception {

      UserRequest request = new UserRequest("antonio", "123456");
      UserResponse response = new UserResponse(1L, "antonio");

      when(createUserUseCase.execute(any())).thenReturn(response);

      mockMvc.perform(post("/users")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(objectMapper.writeValueAsString(request)))
              .andExpect(status().isCreated())
              .andExpect(header().exists("Location"))
              .andExpect(jsonPath("$.id").value(1))
              .andExpect(jsonPath("$.username").value("antonio"));

      verify(createUserUseCase).execute(any());
   }

   @Test
   @DisplayName("POST /users deve retornar 400 quando payload inválido")
   void shouldReturnBadRequestWhenInvalidPayload() throws Exception {

      UserRequest request = new UserRequest("", "123"); // assumindo @NotBlank

      mockMvc.perform(post("/users")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(objectMapper.writeValueAsString(request)))
              .andExpect(status().isBadRequest());

      verifyNoInteractions(createUserUseCase);
   }

   @Test
   @DisplayName("DELETE /users/{id} deve retornar 204")
   void shouldDeleteUser() throws Exception {

      mockMvc.perform(delete("/users/1"))
              .andExpect(status().isNoContent());

      verify(deleteUserUseCase).execute(1L);
   }
}