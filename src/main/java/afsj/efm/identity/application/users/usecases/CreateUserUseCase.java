package afsj.efm.identity.application.users.usecases;

import afsj.efm.identity.application.users.dtos.UserRequest;
import afsj.efm.identity.application.users.dtos.UserResponse;
import afsj.efm.identity.application.users.errors.UserConflicts;
import afsj.efm.identity.application.users.mappers.UserMapper;
import afsj.efm.identity.domain.entities.Role;
import afsj.efm.identity.domain.entities.User;
import afsj.efm.identity.infrastructure.persistence.RoleJpaRepository;
import afsj.efm.identity.infrastructure.persistence.UserJpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateUserUseCase {

   private final UserJpaRepository repository;
   private final PasswordEncoder passwordEncoder;
   private final RoleJpaRepository roleJpaRepository;

   public CreateUserUseCase(
           UserJpaRepository repository,
           PasswordEncoder passwordEncoder,
           RoleJpaRepository roleJpaRepository
   ) {
      this.repository = repository;
      this.passwordEncoder = passwordEncoder;
      this.roleJpaRepository = roleJpaRepository;
   }

   @Transactional
   public UserResponse execute(UserRequest request) {
      if (repository.existsByUsername(request.username()))
         throw UserConflicts.nameAlreadyExists(request.username());

      Role defaultRole = roleJpaRepository.findByName("USER")
              .orElseThrow();

      String encoded = passwordEncoder.encode(request.password());

      var newUser = new User(request.username(), encoded);
      newUser.addRole(defaultRole);

      var saved = repository.save(newUser);

      return UserMapper.toDto(saved);
   }
}
