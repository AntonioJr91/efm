package afsj.efm.identity.application.roles.usecases;

import afsj.efm.identity.application.roles.dtos.RoleResponse;
import afsj.efm.identity.application.roles.errors.RoleNotFound;
import afsj.efm.identity.application.roles.mappers.RoleMapper;
import afsj.efm.identity.infrastructure.persistence.RoleJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetRoleByIdUseCase {

   private final RoleJpaRepository repository;

   public GetRoleByIdUseCase(RoleJpaRepository repository) {
      this.repository = repository;
   }

   @Transactional(readOnly = true)
   public RoleResponse execute(Long id) {
      var role = repository.findById(id)
              .orElseThrow(() -> RoleNotFound.byId(id));

      return RoleMapper.toDto(role);
   }
}
