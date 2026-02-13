package afsj.efm.category.domain.entities;

import afsj.efm.category.domain.exceptions.InvalidCategoryException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "categories")
public class Category {
   private static final int MIN_NAME_LENGTH = 3;
   private static final int MAX_NAME_LENGTH = 50;

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @NotBlank
   @Column(nullable = false, updatable = false, unique = true)
   private String name;

   protected Category() {
      // for ORM only
   }

   public Category(String name) {
      validateName(name);
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
      if(this == o) return true;
      if (!(o instanceof Category category)) return false;
      return getId() != null && getId().equals(category.id);
   }

   @Override
   public int hashCode() {
      return getClass().hashCode();
   }

   private void validateName(String name){
      if(name == null || name.isBlank()) throw new InvalidCategoryException("CATEGORY_NAME_REQUIRED");
      if(name.length() < MIN_NAME_LENGTH) throw new InvalidCategoryException("CATEGORY_NAME_TOO_SHORT");
      if(name.length() > MAX_NAME_LENGTH) throw new InvalidCategoryException("CATEGORY_NAME_TOO_LONG");
   }
}
