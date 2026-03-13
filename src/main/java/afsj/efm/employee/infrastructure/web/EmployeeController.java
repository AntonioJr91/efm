package afsj.efm.employee.infrastructure.web;

import afsj.efm.employee.application.dtos.EmployeeRequest;
import afsj.efm.employee.application.dtos.EmployeeResponse;
import afsj.efm.employee.application.dtos.EmployeeUpdateRequest;
import afsj.efm.employee.application.dtos.EmployeeUpdateResponse;
import afsj.efm.employee.application.usecases.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

   private final ListEmployeesUseCase listEmployeesUseCase;
   private final GetEmployeeByIdUseCase getEmployeeByIdUseCase;
   private final CreateEmployeeUseCase createEmployeeUseCase;
   private final UpdateEmployeeUseCase updateEmployeeUseCase;
   private final DeleteEmployeeUseCase deleteEmployeeUseCase;
   private final TerminationEmployeeUseCase terminationEmployeeUseCase;

   public EmployeeController(
           ListEmployeesUseCase listEmployeesUseCase,
           GetEmployeeByIdUseCase getEmployeeByIdUseCase,
           CreateEmployeeUseCase createEmployeeUseCase,
           UpdateEmployeeUseCase updateEmployeeUseCase,
           DeleteEmployeeUseCase deleteEmployeeUseCase,
           TerminationEmployeeUseCase terminationEmployeeUseCase
   ) {
      this.listEmployeesUseCase = listEmployeesUseCase;
      this.getEmployeeByIdUseCase = getEmployeeByIdUseCase;
      this.createEmployeeUseCase = createEmployeeUseCase;
      this.updateEmployeeUseCase = updateEmployeeUseCase;
      this.deleteEmployeeUseCase = deleteEmployeeUseCase;
      this.terminationEmployeeUseCase = terminationEmployeeUseCase;
   }

   @GetMapping
   public ResponseEntity<List<EmployeeResponse>> list() {
      return ResponseEntity.ok(listEmployeesUseCase.execute());
   }

   @GetMapping("/{id}")
   public ResponseEntity<EmployeeResponse> getById(@PathVariable Long id) {
      return ResponseEntity.ok(getEmployeeByIdUseCase.execute(id));
   }

   @PostMapping
   public ResponseEntity<EmployeeResponse> create(@RequestBody @Valid EmployeeRequest request) {
      var newEmployee = createEmployeeUseCase.execute(request);

      URI location = ServletUriComponentsBuilder
              .fromCurrentRequest()
              .path("/{id}")
              .buildAndExpand(newEmployee.id())
              .toUri();

      return ResponseEntity.created(location).body(newEmployee);
   }

   @PatchMapping("/{id}/phonenumber")
   public ResponseEntity<EmployeeUpdateResponse> update(@PathVariable Long id,
                                                        @RequestBody @Valid EmployeeUpdateRequest request) {
      return ResponseEntity.ok(updateEmployeeUseCase.execute(id, request));
   }

//   @DeleteMapping("/{id}")
//   public ResponseEntity<Void> delete(@PathVariable Long id) {
//      deleteEmployeeUseCase.execute(id);
//      return ResponseEntity.noContent().build();
//   }

   @PostMapping("/{id}/terminate")
   public ResponseEntity<Void> terminate(@PathVariable Long id) {
      terminationEmployeeUseCase.execute(id);
      return ResponseEntity.noContent().build();
   }
}
