package afsj.efm.identity.domain;

import afsj.efm.identity.domain.exceptions.InvalidRoleException;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(unique = true, nullable = false, updatable = false)
   private String username;

   @Column(nullable = false, updatable = false)
   private String password;

   @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
   private List<UsersRoles> usersRoles = new ArrayList<>();

   protected User() {
   }

   public User(String username, String password) {
      validateUsername(username);
      this.username = username;
      this.password = password;
   }

   public Long getId() {
      return id;
   }

   public String getUsername() {
      return username;
   }

   public String getPassword() {
      return password;
   }

   private void validateUsername(String name) {
      if (name == null || name.isBlank()) throw new InvalidRoleException("USER_NAME_IS_REQUIRED");
      if (name.length() < 3) throw new InvalidRoleException("USER_NAME_TOO_SHORT");
      if (name.length() > 50) throw new InvalidRoleException("USER_NAME_TOO_LONG");
   }
}
