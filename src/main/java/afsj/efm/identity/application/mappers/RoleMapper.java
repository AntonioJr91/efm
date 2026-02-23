package afsj.efm.identity.application.mappers;

import afsj.efm.identity.application.dtos.RoleResponse;
import afsj.efm.identity.domain.Role;

import java.util.List;

public final class RoleMapper {

   public static RoleResponse toDto(Role role) {
      return new RoleResponse(
              role.getId(),
              role.getName()
      );
   }

   public static List<RoleResponse> toDtoList(List<Role> list) {
      return list.stream().map(RoleMapper::toDto).toList();
   }
}
