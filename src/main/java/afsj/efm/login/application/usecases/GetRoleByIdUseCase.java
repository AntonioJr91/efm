package afsj.efm.login.application.usecases;

import afsj.efm.login.application.dtos.RoleResponse;
import afsj.efm.login.application.errors.RoleNotFound;
import afsj.efm.login.application.mappers.RoleMapper;
import afsj.efm.login.infrastructure.persistence.RoleJpaRepository;
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
