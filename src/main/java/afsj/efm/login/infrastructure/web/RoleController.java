package afsj.efm.login.infrastructure.web;

import afsj.efm.login.application.dtos.RoleRequest;
import afsj.efm.login.application.dtos.RoleResponse;
import afsj.efm.login.application.usecases.CreateRoleUseCase;
import afsj.efm.login.application.usecases.DeleteRoleUseCase;
import afsj.efm.login.application.usecases.GetRoleByIdUseCase;
import afsj.efm.login.application.usecases.ListRolesUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

   private final ListRolesUseCase listRolesUseCase;
   private final GetRoleByIdUseCase getRoleByIdUseCase;
   private final CreateRoleUseCase createRoleUseCase;
   private final DeleteRoleUseCase deleteRoleUseCase;

   public RoleController(
           ListRolesUseCase listRolesUseCase,
           GetRoleByIdUseCase getRoleByIdUseCase,
           CreateRoleUseCase createRoleUseCase,
           DeleteRoleUseCase deleteRoleUseCase
   ) {
      this.listRolesUseCase = listRolesUseCase;
      this.getRoleByIdUseCase = getRoleByIdUseCase;
      this.createRoleUseCase = createRoleUseCase;
      this.deleteRoleUseCase = deleteRoleUseCase;
   }

   @GetMapping
   public ResponseEntity<List<RoleResponse>> findAll() {
      return ResponseEntity.ok(listRolesUseCase.execute());
   }

   @GetMapping("{id}")
   public ResponseEntity<RoleResponse> findById(@PathVariable Long id) {
      return ResponseEntity.ok(getRoleByIdUseCase.execute(id));
   }

   @PostMapping
   public ResponseEntity<RoleResponse> create(@RequestBody @Valid RoleRequest request) {
      var newRole = createRoleUseCase.execute(request);

      URI location = ServletUriComponentsBuilder
              .fromCurrentRequest()
              .path("/{id}")
              .buildAndExpand(newRole.id())
              .toUri();

      return ResponseEntity.created(location).body(newRole);
   }

   @DeleteMapping("{id}")
   public ResponseEntity<Void> delete(@PathVariable Long id) {
      deleteRoleUseCase.execute(id);
      return ResponseEntity.noContent().build();
   }
}
