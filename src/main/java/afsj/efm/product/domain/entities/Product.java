package afsj.efm.product.domain.entities;

import afsj.efm.product.domain.enums.UnitOfMeasure;
import afsj.efm.product.domain.exceptions.*;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(unique = true, nullable = false, updatable = false)
   private String name;

   @Column(nullable = false)
   private int stock;

   @Enumerated(EnumType.STRING)
   @Column(nullable = false, updatable = false)
   private UnitOfMeasure unitOfMeasure;

   @Column(nullable = false, updatable = false)
   private LocalDate createdAt;

   @OneToMany(mappedBy = "product",
           cascade = CascadeType.ALL,
           orphanRemoval = true)
   private List<StockMovement> stockMovements = new ArrayList<>();

   protected Product() {
   }

   public Product(String name, int stock, UnitOfMeasure unitOfMeasure) {
      validateName(name);
      validateInitialStock(stock);
      validateUnitOfMeasure(unitOfMeasure);

      this.name = name;
      this.stock = stock;
      this.unitOfMeasure = unitOfMeasure;
      this.createdAt = LocalDate.now();
   }

   public Long getId() {
      return id;
   }

   public String getName() {
      return name;
   }

   public LocalDate getCreatedAt() {
      return createdAt;
   }

   public int getAvailableStock() {
      return stock;
   }

   public UnitOfMeasure getUnitOfMeasure() {
      return unitOfMeasure;
   }

   public List<StockMovement> getStockMovements() {
      return List.copyOf(stockMovements);
   }

   public void increaseStock(int quantity) {
      validateMovementQuantity(quantity);

      this.stock += quantity;
      this.stockMovements.add(StockMovement.in(quantity, this));
   }

   public void decreaseStock(int quantity) {
      validateMovementQuantity(quantity);
      validateSufficientStock(quantity);

      this.stock -= quantity;
      this.stockMovements.add(StockMovement.out(quantity, this));
   }

   private void validateName(String name) {
      if (name == null || name.isBlank()) throw new InvalidProductNameException("PRODUCT_NAME_REQUIRED");
      if (name.length() < 3) throw new InvalidProductNameException("PRODUCT_NAME_TOO_SHORT");
      if (name.length() > 50) throw new InvalidProductNameException("PRODUCT_NAME_TOO_LONG");
   }

   private void validateInitialStock(int quantity) {
      if (quantity < 0) throw new InvalidInitialStockException("INITIAL_STOCK_CANNOT_BE_NEGATIVE");
   }

   private void validateMovementQuantity(int quantity) {
      if (quantity <= 0) throw new InvalidMovementQuantityException("MOVEMENT_QUANTITY_MUST_BE_GREATER_THAN_ZERO");
   }

   private void validateSufficientStock(int quantity) {
      if (quantity > stock) throw new InsufficientStockException("INSUFFICIENT_STOCK_AVAILABLE");
   }

   private void validateUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
      if (unitOfMeasure == null) throw new UnitOfMeasureRequiredException("UNIT_OF_MEASURE_REQUIRED");
   }
}
