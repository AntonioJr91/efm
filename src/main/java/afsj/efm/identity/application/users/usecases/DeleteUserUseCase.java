package afsj.efm.identity.application.users.usecases;

import afsj.efm.identity.application.users.errors.UserNotFound;
import afsj.efm.identity.infrastructure.persistence.UserJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteUserUseCase {

   private final UserJpaRepository repository;

   public DeleteUserUseCase(UserJpaRepository repository) {
      this.repository = repository;
   }

   @Transactional
   public void execute(Long id) {
      if (!repository.existsById(id))
         throw UserNotFound.byId(id);

      repository.deleteById(id);
   }
}
