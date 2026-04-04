package afsj.efm.identity.infrastructure.bootstrap;

import afsj.efm.identity.domain.entities.Role;
import afsj.efm.identity.domain.entities.User;
import afsj.efm.identity.infrastructure.persistence.RoleJpaRepository;
import afsj.efm.identity.infrastructure.persistence.UserJpaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Profile("docker")
public class DockerAdminSeeder implements ApplicationRunner {

   private final UserJpaRepository userRepository;
   private final RoleJpaRepository roleRepository;
   private final PasswordEncoder passwordEncoder;

   @Value("${efm.docker.seed.admin.username}")
   private String adminUsername;

   @Value("${efm.docker.seed.admin.password}")
   private String adminPassword;

   public DockerAdminSeeder(
           UserJpaRepository userRepository,
           RoleJpaRepository roleRepository,
           PasswordEncoder passwordEncoder
   ) {
      this.userRepository = userRepository;
      this.roleRepository = roleRepository;
      this.passwordEncoder = passwordEncoder;
   }

   @Override
   @Transactional
   public void run(ApplicationArguments args) {
      Role adminRole = roleRepository.findByName("ADMIN")
              .orElseGet(() -> roleRepository.save(new Role("ADMIN")));

      User adminUser = userRepository.findByUsername(adminUsername)
              .orElseGet(() -> userRepository.save(
                      new User(adminUsername, passwordEncoder.encode(adminPassword))
              ));

      if (adminUser.getRoles().stream().noneMatch(role -> "ADMIN".equals(role.getName()))) {
         adminUser.addRole(adminRole);
         userRepository.save(adminUser);
      }
   }
}
