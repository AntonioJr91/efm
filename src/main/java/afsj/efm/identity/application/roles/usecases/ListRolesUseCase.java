package afsj.efm.identity.application.roles.usecases;

import afsj.efm.identity.application.roles.dtos.RoleResponse;
import afsj.efm.identity.application.roles.mappers.RoleMapper;
import afsj.efm.identity.infrastructure.persistence.RoleJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ListRolesUseCase {

   private final RoleJpaRepository repository;

   public ListRolesUseCase(RoleJpaRepository repository) {
      this.repository = repository;
   }

   @Transactional(readOnly = true)
   public List<RoleResponse> execute() {
      return RoleMapper.toDtoList(repository.findAll());
   }
}
