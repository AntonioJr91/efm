package afsj.efm.production.domain.entities;

import afsj.efm.production.domain.exceptions.InvalidProductionException;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "productions")
public class Production {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(nullable = false, updatable = false)
   private Long areaId;

   @Column(nullable = false, updatable = false)
   private Long productId;

   @Column(nullable = false, updatable = false)
   private int quantity;

   @Column(nullable = false, updatable = false)
   private LocalDate createdAt;

   @Column(updatable = false)
   private String observation;

   protected Production() {
   }

   public Production(Long areaId, Long productId, int quantity, String observation) {
      validateId(areaId, "area");
      validateId(productId, "product");
      validateQuantity(quantity);
      validateObservation(observation);

      this.areaId = areaId;
      this.productId = productId;
      this.quantity = quantity;
      this.createdAt = LocalDate.now();
      this.observation = observation == null ? "" : observation.trim();
   }

   public Long getId() {
      return id;
   }

   public Long getAreaId() {
      return areaId;
   }

   public Long getProductId() {
      return productId;
   }

   public int getQuantity() {
      return quantity;
   }

   public LocalDate getCreatedAt() {
      return createdAt;
   }

   public String getObservation() {
      return observation;
   }

   private void validateId(Long property, String name) {
      if (property == null) throw new InvalidProductionException("%s_IS_REQUIRED".formatted(name.toUpperCase()));
      if (property <= 0) throw new InvalidProductionException("%s_MUST_BE_POSITIVE".formatted(name.toUpperCase()));
   }

   private void validateQuantity(int quantity) {
      if (quantity <= 0) throw new InvalidProductionException("QUANTITY_MUST_BE_POSITIVE");
   }

   private void validateObservation(String observation) {
      if (observation != null && observation.trim().length() > 255) {
         throw new InvalidProductionException("OBSERVATION_MAX_LENGTH_EXCEEDED");
      }
   }
}
