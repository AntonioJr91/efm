package afsj.efm.employee.application.usecases;

import afsj.efm.employee.application.dtos.EmployeeUpdateRequest;
import afsj.efm.employee.application.dtos.EmployeeUpdateResponse;
import afsj.efm.employee.application.errors.EmployeeConflicts;
import afsj.efm.employee.application.errors.EmployeeNotFound;
import afsj.efm.employee.application.mappers.EmployeeMapper;
import afsj.efm.employee.domain.entities.PhoneNumber;
import afsj.efm.employee.infrastructure.persistence.EmployeeJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class UpdateEmployeeUseCase {

   private final EmployeeJpaRepository repository;

   public UpdateEmployeeUseCase(EmployeeJpaRepository repository) {
      this.repository = repository;
   }

   @Transactional
   public EmployeeUpdateResponse execute(Long id, EmployeeUpdateRequest request) {
      var employee = repository.findById(id)
              .orElseThrow(() -> EmployeeNotFound.byId(id));

      if (request.phoneNumber() == null) return EmployeeMapper.toDtoUpdate(employee);

      String currentPhone = employee.getPhoneNumberValue();
      String newPhoneRaw = request.phoneNumber();

      if (newPhoneRaw.isBlank()) {
         if (currentPhone != null) {
            employee.changePhoneNumber(null);
         }
         return EmployeeMapper.toDtoUpdate(employee);
      }

      if (Objects.equals(currentPhone, newPhoneRaw)) {
         return EmployeeMapper.toDtoUpdate(employee);
      }

      var newPhoneNumber = new PhoneNumber(newPhoneRaw);

      if (repository.existsByPhoneNumber(newPhoneNumber))
         throw EmployeeConflicts.phoneNumberAlreadyExists(newPhoneNumber);

      employee.changePhoneNumber(newPhoneNumber);

      return EmployeeMapper.toDtoUpdate(employee);
   }
}
