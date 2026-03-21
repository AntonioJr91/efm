package afsj.efm.product.domain.entities;

import afsj.efm.product.domain.enums.MovementType;
import afsj.efm.product.domain.exceptions.InvalidProductException;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movements")
public class StockMovement {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Enumerated(EnumType.STRING)
   @Column(nullable = false)
   private MovementType movementType;

   @Column(nullable = false)
   private int quantity;

   @Column(nullable = false)
   private LocalDateTime createdAt;

   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @JoinColumn(name = "product_id", nullable = false)
   private Product product;

   protected StockMovement() {
   }

   private StockMovement(MovementType movementType, int quantity, Product product) {
      validate(movementType, quantity);
      this.movementType = movementType;
      this.quantity = quantity;
      this.createdAt = LocalDateTime.now();
      this.product = product;
   }

   public static StockMovement in(int quantity, Product product) {
      return new StockMovement(MovementType.IN, quantity, product);
   }

   public static StockMovement out(int quantity, Product product) {
      return new StockMovement(MovementType.OUT, quantity, product);
   }

   private static void validate(MovementType type, int value) {
      if (type == null) throw new InvalidProductException("MOVEMENT_TYPE_REQUIRED");
      if (value <= 0) throw new InvalidProductException("QUANTITY_MUST_BE_GREATER_THAN_ZERO");
   }

   public MovementType getMovementType() {
      return movementType;
   }

   public int getQuantity() {
      return quantity;
   }

   public LocalDateTime getCreatedAt() {
      return createdAt;
   }
}
