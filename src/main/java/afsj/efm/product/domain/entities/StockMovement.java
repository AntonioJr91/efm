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

   private StockMovement(Product product, int quantity, MovementType movementType) {
      validate(product, quantity, movementType);
      this.product = product;
      this.quantity = quantity;
      this.movementType = movementType;
      this.createdAt = LocalDateTime.now();
   }

   public static StockMovement in(Product product, int quantity) {
      return new StockMovement(product, quantity, MovementType.IN);
   }

   public static StockMovement out(Product product, int quantity) {
      return new StockMovement(product, quantity, MovementType.OUT);
   }

   private static void validate(Product product, int value, MovementType type) {
      if (product == null) throw new InvalidProductException("PRODUCT_REQUIRED");
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
