package afsj.efm.service_order.domain.entities;

import jakarta.persistence.*;

@Entity
public class FarmArea {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(unique = true, nullable = false, updatable = false)
   private String name;

   protected FarmArea() {
   }

   public FarmArea(String name) {
      validateAreaName(name);
      this.name = name;
   }

   public Long getId() {
      return id;
   }

   public String getName() {
      return name;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof FarmArea farmArea)) return false;
      return id != null && id.equals(farmArea.id);
   }

   @Override
   public int hashCode() {
      return getClass().hashCode();
   }

   private void validateAreaName(String name) {
      if (name == null || name.isBlank()) throw new IllegalArgumentException("AREA_NAME_IS_REQUIRED");
      if (name.length() < 3) throw new IllegalArgumentException("AREA_NAME_TOO_SHORT");
      if (name.length() > 50) throw new IllegalArgumentException("AREA_NAME_TOO_LONG");
   }
}
