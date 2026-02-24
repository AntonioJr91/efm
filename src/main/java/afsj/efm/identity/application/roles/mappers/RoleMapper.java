package afsj.efm.identity.application.roles.mappers;

import afsj.efm.identity.application.roles.dtos.RoleResponse;
import afsj.efm.identity.domain.entities.Role;

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
