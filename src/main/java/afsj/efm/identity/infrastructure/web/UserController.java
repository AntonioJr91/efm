package afsj.efm.identity.infrastructure.web;

import afsj.efm.identity.application.users.dtos.UserRequest;
import afsj.efm.identity.application.users.dtos.UserResponse;
import afsj.efm.identity.application.users.usecases.CreateUserUseCase;
import afsj.efm.identity.application.users.usecases.DeleteUserUseCase;
import afsj.efm.identity.application.users.usecases.GetUserByIdUseCase;
import afsj.efm.identity.application.users.usecases.ListUsersUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

   private final ListUsersUseCase listUsersUseCase;
   private final GetUserByIdUseCase getUserByIdUseCase;
   private final CreateUserUseCase createUserUseCase;
   private final DeleteUserUseCase deleteUserUseCase;

   public UserController(
           ListUsersUseCase listUsersUseCase,
           GetUserByIdUseCase getUserByIdUseCase,
           CreateUserUseCase createUserUseCase,
           DeleteUserUseCase deleteUserUseCase
   ) {
      this.listUsersUseCase = listUsersUseCase;
      this.getUserByIdUseCase = getUserByIdUseCase;
      this.createUserUseCase = createUserUseCase;
      this.deleteUserUseCase = deleteUserUseCase;
   }

   @GetMapping
   public ResponseEntity<List<UserResponse>> findAll() {
      return ResponseEntity.ok(listUsersUseCase.execute());
   }

   @GetMapping("{id}")
   public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
      return ResponseEntity.ok(getUserByIdUseCase.execute(id));
   }

   @PostMapping
   public ResponseEntity<UserResponse> create(@RequestBody @Valid UserRequest request) {
      var newUser = createUserUseCase.execute(request);

      URI location = ServletUriComponentsBuilder
              .fromCurrentRequest()
              .path("/{id}")
              .buildAndExpand(newUser.id())
              .toUri();

      return ResponseEntity.created(location).body(newUser);
   }

   @DeleteMapping("{id}")
   public ResponseEntity<Void> delete(@PathVariable Long id) {
      deleteUserUseCase.execute(id);
      return ResponseEntity.noContent().build();
   }
}
