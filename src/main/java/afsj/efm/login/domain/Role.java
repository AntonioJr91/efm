package afsj.efm.login.domain;

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
      this.name = name;
   }

   public Long getId() {
      return id;
   }

   public String getName() {
      return name;
   }
}
