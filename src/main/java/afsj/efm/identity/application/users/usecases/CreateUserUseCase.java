package afsj.efm.identity.application.users.usecases;

import afsj.efm.identity.application.users.dtos.UserRequest;
import afsj.efm.identity.application.users.dtos.UserResponse;
import afsj.efm.identity.application.users.errors.UserConflicts;
import afsj.efm.identity.application.users.mappers.UserMapper;
import afsj.efm.identity.domain.entities.User;
import afsj.efm.identity.infrastructure.persistence.UserJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateUserUseCase {

   private final UserJpaRepository repository;

   public CreateUserUseCase(UserJpaRepository repository) {
      this.repository = repository;
   }

   @Transactional
   public UserResponse execute(UserRequest request) {
      if (repository.existsByUsername(request.username()))
         throw UserConflicts.nameAlreadyExists(request.username());

      var newUser = new User(request.username(), request.password());

      var saved = repository.save(newUser);

      return UserMapper.toDto(saved);
   }
}
