package afsj.efm.identity.application.roles.usecases;

import afsj.efm.identity.application.roles.dtos.RoleRequest;
import afsj.efm.identity.application.roles.dtos.RoleResponse;
import afsj.efm.identity.application.roles.errors.RoleConflicts;
import afsj.efm.identity.application.roles.mappers.RoleMapper;
import afsj.efm.identity.domain.entities.Role;
import afsj.efm.identity.infrastructure.persistence.RoleJpaRepository;
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
