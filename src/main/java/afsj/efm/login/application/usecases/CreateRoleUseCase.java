package afsj.efm.login.application.usecases;

import afsj.efm.login.application.dtos.RoleRequest;
import afsj.efm.login.application.dtos.RoleResponse;
import afsj.efm.login.application.errors.RoleConflicts;
import afsj.efm.login.application.mappers.RoleMapper;
import afsj.efm.login.domain.Role;
import afsj.efm.login.infrastructure.persistence.RoleJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateRoleUseCase {

   private final RoleJpaRepository repository;

   public CreateRoleUseCase(RoleJpaRepository repository) {
      this.repository = repository;
   }

   @Transactional
   public RoleResponse execute(RoleRequest request) {
      if (repository.existsByName(request.roleName()))
         throw RoleConflicts.nameAlreadyExists(request.roleName());

      var newRole = new Role(request.roleName());
      var saved = repository.save(newRole);

      return RoleMapper.toDto(saved);
   }
}
