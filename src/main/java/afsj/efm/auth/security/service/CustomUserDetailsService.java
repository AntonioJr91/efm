package afsj.efm.auth.security.service;

import afsj.efm.auth.security.model.SecurityUSer;
import afsj.efm.identity.application.users.errors.UserNotFound;
import afsj.efm.identity.domain.entities.User;
import afsj.efm.identity.infrastructure.persistence.UserJpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

   private final UserJpaRepository repository;

   public CustomUserDetailsService(UserJpaRepository repository) {
      this.repository = repository;
   }

   @Override
   @Transactional(readOnly = true)
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      User user = repository.findByUsername(username)
              .orElseThrow(() -> UserNotFound.byName(username));

      return new SecurityUSer(user);
   }
}
