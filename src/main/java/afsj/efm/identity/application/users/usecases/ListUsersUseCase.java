package afsj.efm.identity.application.users.usecases;

import afsj.efm.identity.application.users.dtos.UserResponse;
import afsj.efm.identity.application.users.mappers.UserMapper;
import afsj.efm.identity.infrastructure.persistence.UserJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ListUsersUseCase {

   private final UserJpaRepository repository;

   public ListUsersUseCase(UserJpaRepository repository) {
      this.repository = repository;
   }

   @Transactional(readOnly = true)
   public List<UserResponse> execute() {
      return UserMapper.toDtoList(repository.findAll());
   }
}
