package afsj.efm.employee.application.mappers;

import afsj.efm.employee.application.dtos.EmployeeResponse;
import afsj.efm.employee.application.dtos.EmployeeUpdateResponse;
import afsj.efm.employee.domain.entities.Employee;
import afsj.efm.employee.domain.entities.PhoneNumber;

import java.util.List;

public final class EmployeeMapper {
   public static EmployeeResponse toDto(Employee employee) {
      return new EmployeeResponse(
              employee.getId(),
              employee.getFirstName(),
              employee.getLastName(),
              employee.getCpf().value(),
              employee.getPhoneNumber().map(PhoneNumber::value).orElse(null),
              employee.getJobRole(),
              employee.getContractType(),
              employee.getHireDate(),
              employee.getStatus(),
              employee.getTerminationDate()
      );
   }

   public static List<EmployeeResponse> toDtoList(List<Employee> list) {
      return list.stream().map(EmployeeMapper::toDto).toList();
   }

   public static EmployeeUpdateResponse toDtoUpdate(Employee employee){
      return new EmployeeUpdateResponse(employee.getPhoneNumber().map(PhoneNumber::value).orElse(null));
   }
}
