package afsj.efm.identity.infrastructure.persistence;

import afsj.efm.identity.domain.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleJpaRepository extends JpaRepository<Role, Long> {
   boolean existsByName(String name);
}
