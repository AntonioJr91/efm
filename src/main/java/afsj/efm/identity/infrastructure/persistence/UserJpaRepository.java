package afsj.efm.identity.infrastructure.persistence;

import afsj.efm.identity.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {
   boolean existsByUsername(String username);
   Optional<User> findByUsername(String username);

   @Query("""
   SELECT u FROM User u
   LEFT JOIN FETCH u.usersRoles ur
   LEFT JOIN FETCH ur.role
   WHERE u.username = :username
""")
   Optional<User> findByUsernameWithRoles(String username);
}
