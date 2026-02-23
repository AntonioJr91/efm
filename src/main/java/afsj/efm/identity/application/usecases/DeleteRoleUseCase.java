package afsj.efm.identity.application.usecases;

import afsj.efm.identity.application.errors.RoleNotFound;
import afsj.efm.identity.infrastructure.persistence.RoleJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteRoleUseCase {

   private final RoleJpaRepository repository;

   public DeleteRoleUseCase(RoleJpaRepository repository) {
      this.repository = repository;
   }

   @Transactional
   public void execute(Long id) {
      if (!repository.existsById(id)) throw RoleNotFound.byId(id);
      repository.deleteById(id);
   }
}
