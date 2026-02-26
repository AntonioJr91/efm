package afsj.efm.identity.infrastructure.persistence;

import afsj.efm.identity.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {
   boolean existsByUsername(String username);
   Optional<User> findByUsername(String username);
}
