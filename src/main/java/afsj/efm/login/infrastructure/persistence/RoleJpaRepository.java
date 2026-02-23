package afsj.efm.login.infrastructure.persistence;

import afsj.efm.login.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleJpaRepository extends JpaRepository<Role, Long> {
   boolean existsByName(String name);
}
