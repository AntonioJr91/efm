package afsj.efm.identity.infrastructure.persistence;

import afsj.efm.identity.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {
   boolean existsByUsername(String username);
}
