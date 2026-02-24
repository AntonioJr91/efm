package afsj.efm.identity.domain.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "users_roles",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role_id"})
)
public class UsersRoles {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   Long id;

   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @JoinColumn(name = "user_id", nullable = false)
   User user;

   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @JoinColumn(name = "role_id", nullable = false)
   Role role;

   protected UsersRoles() {
   }

   public UsersRoles(User user, Role role) {
      this.user = user;
      this.role = role;
   }

   public Long getId() {
      return id;
   }

   public User getUser() {
      return user;
   }

   public Role getRole() {
      return role;
   }
}
