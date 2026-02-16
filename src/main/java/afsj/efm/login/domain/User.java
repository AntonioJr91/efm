package afsj.efm.login.domain;

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
}
