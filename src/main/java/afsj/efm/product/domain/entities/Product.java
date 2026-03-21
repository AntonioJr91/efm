package afsj.efm.product.domain.entities;

import afsj.efm.category.domain.entities.Category;
import afsj.efm.product.domain.enums.ProductOrigin;
import afsj.efm.product.domain.enums.StockStatus;
import afsj.efm.product.domain.enums.UnitOfMeasure;
import afsj.efm.product.domain.exceptions.InvalidProductException;
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

   @Column(nullable = false)
   private int minimumStock;

   @Enumerated(EnumType.STRING)
   @Column(nullable = false, updatable = false)
   private UnitOfMeasure unitOfMeasure;

   @Enumerated(EnumType.STRING)
   @Column(nullable = false, updatable = false)
   private ProductOrigin productOrigin;

   @Column(nullable = false, updatable = false)
   private LocalDate createdAt;

   @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
   private List<StockMovement> stockMovements = new ArrayList<>();

   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @JoinColumn(name = "category_id", nullable = false)
   private Category category;

   protected Product() {
   }

   public Product(
           String name,
           int stock,
           int minimumStock,
           UnitOfMeasure unitOfMeasure,
           ProductOrigin productOrigin,
           Category category
   ) {
      validateName(name);
      validateInitialStock(stock);
      validateMinimumStock(minimumStock);
      validateUnitOfMeasure(unitOfMeasure);
      validateProductOrigin(productOrigin);
      validateCategory(category);

      this.name = name;
      this.stock = stock;
      this.minimumStock = minimumStock;
      this.unitOfMeasure = unitOfMeasure;
      this.productOrigin = productOrigin;
      this.createdAt = LocalDate.now();
      this.category = category;
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

   public int getStock() {
      return stock;
   }

   public int getMinimumStock() {
      return minimumStock;
   }

   public UnitOfMeasure getUnitOfMeasure() {
      return unitOfMeasure;
   }

   public ProductOrigin getProductOrigin() {
      return productOrigin;
   }

   public List<StockMovement> getStockMovements() {
      return List.copyOf(stockMovements);
   }

   public Category getCategory() {
      return category;
   }

   public void increaseStock(int quantity) {
      validateMovementQuantity(quantity);

      this.stock += quantity;
      this.stockMovements.add(StockMovement.in(this, quantity));
   }

   public void decreaseStock(int quantity) {
      validateMovementQuantity(quantity);
      validateSufficientStock(quantity);

      this.stock -= quantity;
      this.stockMovements.add(StockMovement.out(this, quantity));
   }

   private void validateName(String name) {
      if (name == null || name.isBlank()) throw new InvalidProductException("PRODUCT_NAME_REQUIRED");
      if (name.length() < 3) throw new InvalidProductException("PRODUCT_NAME_TOO_SHORT");
      if (name.length() > 50) throw new InvalidProductException("PRODUCT_NAME_TOO_LONG");
   }

   private void validateInitialStock(int quantity) {
      if (quantity < 0) throw new InvalidProductException("INITIAL_STOCK_CANNOT_BE_NEGATIVE");
   }

   private void validateMovementQuantity(int quantity) {
      if (quantity <= 0) throw new InvalidProductException("MOVEMENT_QUANTITY_MUST_BE_GREATER_THAN_ZERO");
   }

   private void validateSufficientStock(int quantity) {
      if (quantity > stock) throw new InvalidProductException("INSUFFICIENT_STOCK_AVAILABLE");
   }

   private void validateUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
      if (unitOfMeasure == null) throw new InvalidProductException("UNIT_OF_MEASURE_REQUIRED");
   }

   private void validateProductOrigin(ProductOrigin productOrigin) {
      if (productOrigin == null) throw new InvalidProductException("PRODUCT_ORIGIN_REQUIRED");
   }

   private void validateCategory(Category category) {
      if (category == null) throw new InvalidProductException("CATEGORY_IS_REQUIRED");
   }

   private void validateMinimumStock(int minimumStock) {
      if (minimumStock < 1) throw new InvalidProductException("MINIMUM_STOCK_MUST_BE_GREATER_THAN_ZERO");
   }

   public StockStatus getStockStatus() {
      if (stock == 0) return StockStatus.OUT_OF_STOCK;
      if (stock <= minimumStock) return StockStatus.LOW_STOCK;
      return StockStatus.IN_STOCK;
   }
}
