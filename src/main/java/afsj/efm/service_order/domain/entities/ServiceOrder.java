package afsj.efm.service_order.domain.entities;

import afsj.efm.employee.domain.entities.Employee;
import afsj.efm.product.domain.entities.Product;
import afsj.efm.service_order.domain.enums.StatusOrder;
import afsj.efm.service_order.domain.exceptions.InvalidServiceOrderException;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ServiceOrder {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @JoinColumn(name = "employee_id", nullable = false)
   private Employee employee;

   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @JoinColumn(name = "farm_area_id", nullable = false)
   private FarmArea farmArea;

   @Enumerated(EnumType.STRING)
   @Column(nullable = false, updatable = true)
   private StatusOrder statusOrder;

   @Embedded
   private ServiceType serviceType;

   @Column(nullable = false, updatable = false)
   private LocalDate createdAt;

   @Column(nullable = true, updatable = true)
   private LocalDate finishedAt;

   @OneToMany(
           mappedBy = "serviceOrder",
           cascade = CascadeType.ALL,
           orphanRemoval = true,
           fetch = FetchType.LAZY
   )
   private List<ServiceOrderItem> items = new ArrayList<>();

   protected ServiceOrder() {
   }

   public ServiceOrder(Employee employee, FarmArea farmArea, ServiceType serviceType) {
      validateEmployee(employee);
      validateFarmArea(farmArea);
      validateServiceType(serviceType);

      this.employee = employee;
      this.farmArea = farmArea;
      this.statusOrder = StatusOrder.IN_PROGRESS;
      this.serviceType = serviceType;
      this.createdAt = LocalDate.now();
      this.finishedAt = null;
   }

   public Long getId() {
      return id;
   }

   public Employee getEmployee() {
      return employee;
   }

   public FarmArea getFarmArea() {
      return farmArea;
   }

   public StatusOrder getStatusOrder() {
      return statusOrder;
   }

   public ServiceType getServiceType() {
      return serviceType;
   }

   public LocalDate getCreatedAt() {
      return createdAt;
   }

   public LocalDate getFinishedAt() {
      return finishedAt;
   }

   public List<ServiceOrderItem> getItems() {
      return List.copyOf(items);
   }

   public void addItem(Product product, int quantity) {
      ensureEditable();
      this.items.add(new ServiceOrderItem(this, product, quantity));
   }

   public void increaseItemQuantity(Long itemId, int quantity) {
      ensureEditable();

      ServiceOrderItem item = getItemOrThrow(itemId);
      item.increase(quantity);
   }

   public void decreaseItemQuantity(Long itemId, int quantity) {
      ensureEditable();

      ServiceOrderItem item = getItemOrThrow(itemId);
      item.decrease(quantity);
   }

   public void removeItem(Long itemId) {
      ensureEditable();

      ServiceOrderItem item = getItemOrThrow(itemId);
      items.remove(item);
   }

   public void complete(LocalDate finishedAt) {
      ensureInProgress();
      validateTerminateDate(finishedAt);
      this.statusOrder = StatusOrder.COMPLETED;
      this.finishedAt = finishedAt;
   }

   public void cancel(LocalDate finishedAt){
      ensureEditable();
      validateTerminateDate(finishedAt);
      this.statusOrder = StatusOrder.CANCELED;
      this.finishedAt = finishedAt;
   }

   public boolean isCompleted(){
      return this.statusOrder == StatusOrder.COMPLETED;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof ServiceOrder serviceOrder)) return false;
      return id != null && id.equals(serviceOrder.id);
   }

   @Override
   public int hashCode() {
      return getClass().hashCode();
   }

   private void validateEmployee(Employee employee) {
      if (employee == null) throw new InvalidServiceOrderException("EMPLOYEE_IS_REQUIRED");
   }

   private void validateFarmArea(FarmArea farmArea) {
      if (farmArea == null) throw new InvalidServiceOrderException("FARM_AREA_IS_REQUIRED");
   }

   private void validateServiceType(ServiceType serviceType) {
      if (serviceType == null) throw new InvalidServiceOrderException("SERVICE_TYPE_IS_REQUIRED");
   }

   private void validateTerminateDate(LocalDate terminateDate) {
      if (terminateDate.isBefore(createdAt)) throw new InvalidServiceOrderException("INVALID_TERMINATE_DATE");
      if (statusOrder != StatusOrder.IN_PROGRESS)
         throw new InvalidServiceOrderException("CANNOT_CHANGE_SERVICE_ORDER_COMPLETED_OR_CANCELED");
   }

   private void ensureEditable() {
      if (statusOrder == StatusOrder.COMPLETED || statusOrder == StatusOrder.CANCELED) {
         throw new InvalidServiceOrderException("SERVICE_ORDER_NOT_EDITABLE");
      }
   }

   private void ensureInProgress() {
      if (statusOrder != StatusOrder.IN_PROGRESS) {
         throw new InvalidServiceOrderException("SERVICE_ORDER_NOT_IN_PROGRESS");
      }
   }

   private ServiceOrderItem getItemOrThrow(Long itemId) {
      return items.stream()
              .filter(item -> item.getId().equals(itemId))
              .findFirst()
              .orElseThrow(() -> new InvalidServiceOrderException("SERVICE_ORDER_ITEM_NOT_FOUND"));
   }
}
