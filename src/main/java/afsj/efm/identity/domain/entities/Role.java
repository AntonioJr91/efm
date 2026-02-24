package afsj.efm.identity.domain.entities;

import afsj.efm.identity.domain.exceptions.InvalidRoleException;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(unique = true, nullable = false, updatable = false)
   private String name;

   @OneToMany(mappedBy = "role")
   private List<UsersRoles> usersRoles = new ArrayList<>();

   public Role() {
   }

   public Role(String name) {
      validateRoleName(name);
      this.name = normalize(name);
   }

   public Long getId() {
      return id;
   }

   public String getName() {
      return name;
   }

   private void validateRoleName(String name) {
      if (name == null || name.isBlank()) throw new InvalidRoleException("ROLE_NAME_IS_REQUIRED");
      if (name.length() < 3) throw new InvalidRoleException("ROLE_NAME_TOO_SHORT");
      if (name.length() > 50) throw new InvalidRoleException("ROLE_NAME_TOO_LONG");
   }

   private String normalize(String name) {
      return name.trim().toUpperCase();
   }
}
