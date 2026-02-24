package afsj.efm.identity.application.users.usecases;

import afsj.efm.identity.application.users.dtos.UserResponse;
import afsj.efm.identity.application.users.errors.UserNotFound;
import afsj.efm.identity.application.users.mappers.UserMapper;
import afsj.efm.identity.infrastructure.persistence.UserJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetUserByIdUseCase {

   private final UserJpaRepository repository;

   public GetUserByIdUseCase(UserJpaRepository repository) {
      this.repository = repository;
   }

   @Transactional(readOnly = true)
   public UserResponse execute(Long id) {
      var user = repository.findById(id)
              .orElseThrow(() -> UserNotFound.byId(id));
      return UserMapper.toDto(user);
   }
}
