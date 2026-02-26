package afsj.efm.identity.domain.entities;

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

   public List<Role> getRoles() {
      return usersRoles
              .stream()
              .map(UsersRoles::getRole)
              .toList();
   }

   public void addRole(Role role){
      boolean alreadyAssigned = usersRoles.stream()
              .anyMatch(ur -> ur.getRole().equals(role));
      if(alreadyAssigned) return;

      usersRoles.add(new UsersRoles(this, role));
   }

   private void validateUsername(String name) {
      if (name == null || name.isBlank()) throw new InvalidRoleException("USER_NAME_IS_REQUIRED");
      if (name.length() < 3) throw new InvalidRoleException("USER_NAME_TOO_SHORT");
      if (name.length() > 50) throw new InvalidRoleException("USER_NAME_TOO_LONG");
   }
}
