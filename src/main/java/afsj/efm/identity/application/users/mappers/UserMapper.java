package afsj.efm.identity.application.users.mappers;

import afsj.efm.identity.application.users.dtos.UserResponse;
import afsj.efm.identity.domain.entities.User;
import afsj.efm.identity.domain.entities.UsersRoles;

import java.util.List;

public final class UserMapper {

   public static UserResponse toDto(User user) {
      return new UserResponse(
              user.getId(),
              user.getUsername()
      );
   }

   public static List<UserResponse> toDtoList(List<User> list) {
      return list.stream().map(UserMapper::toDto).toList();
   }
}
