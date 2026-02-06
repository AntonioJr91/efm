package afsj.efm.service_order.domain.entities;

import afsj.efm.product.domain.entities.Product;
import jakarta.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(
        columnNames = {"service_order_id", "product_id"}
))
public class ServiceOrderItem {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @JoinColumn(name = "service_order_id", nullable = false)
   private ServiceOrder serviceOrder;

   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @JoinColumn(name = "product_id", nullable = false)
   private Product product;

   @Column(nullable = false, updatable = true)
   private int quantity;

   protected ServiceOrderItem() {
   }

   ServiceOrderItem(ServiceOrder serviceOrder, Product product, int quantity) {
      validateServiceOrder(serviceOrder);
      validateProduct(product);
      validateQuantity(quantity);

      this.serviceOrder = serviceOrder;
      this.product = product;
      this.quantity = quantity;
   }

   public Long getId() {
      return id;
   }

   public ServiceOrder getServiceOrder() {
      return serviceOrder;
   }

   public Product getProduct() {
      return product;
   }

   public int getQuantity() {
      return quantity;
   }

   public void increase(int quantity) {
      validateQuantity(quantity);
      this.quantity += quantity;
   }

   public void decrease(int quantity) {
      validateQuantity(quantity);

      int result = this.quantity - quantity;
      validateResultingQuantity(result);

      this.quantity = result;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof ServiceOrderItem orderItem)) return false;
      return id != null && id.equals(orderItem.id);
   }

   @Override
   public int hashCode() {
      return getClass().hashCode();
   }

   private void validateServiceOrder(ServiceOrder serviceOrder) {
      if (serviceOrder == null) throw new IllegalArgumentException("SERVICE_ORDER_IS_REQUIRED");
   }

   private void validateProduct(Product product) {
      if (product == null) throw new IllegalArgumentException("PRODUCT_IS_REQUIRED");
   }

   private void validateQuantity(int quantity) {
      if (quantity <= 0) throw new IllegalArgumentException("QUANTITY_MUST_BE_GREATER_THAN_ZERO");
   }

   private void validateResultingQuantity(int resultingQuantity) {
      if (resultingQuantity <= 0)
         throw new IllegalStateException("QUANTITY_CANNOT_BE_ZERO_OR_NEGATIVE");
   }

}
