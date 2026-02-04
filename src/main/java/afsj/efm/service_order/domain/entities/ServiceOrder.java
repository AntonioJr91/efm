package afsj.efm.service_order.domain.entities;

import afsj.efm.employee.domain.entities.Employee;
import afsj.efm.service_order.domain.enums.StatusOrder;
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

   public void addItem(ServiceOrderItem item) {
      ensureEditable();
      this.items.add(item);
   }

   public void complete(LocalDate finishedAt) {
      ensureInProgress();
      validateTerminateDate(finishedAt);
      this.statusOrder = StatusOrder.COMPLETED;
      this.finishedAt = finishedAt;
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
      if (employee == null) throw new IllegalArgumentException("EMPLOYEE_IS_REQUIRED");
   }

   private void validateFarmArea(FarmArea farmArea) {
      if (farmArea == null) throw new IllegalArgumentException("FARM_AREA_IS_REQUIRED");
   }

   private void validateServiceType(ServiceType serviceType) {
      if (serviceType == null) throw new IllegalArgumentException("SERVICE_TYPE_IS_REQUIRED");
   }

   private void validateTerminateDate(LocalDate terminateDate) {
      if (terminateDate.isBefore(createdAt)) throw new IllegalArgumentException("INVALID_TERMINATE_DATE");
      if (statusOrder != StatusOrder.IN_PROGRESS)
         throw new IllegalArgumentException("CANNOT_CHANGE_SERVICE_ORDER_COMPLETED_OR_CANCELED");
   }

   private void ensureEditable() {
      if (statusOrder == StatusOrder.COMPLETED || statusOrder == StatusOrder.CANCELED) {
         throw new IllegalStateException("SERVICE_ORDER_NOT_EDITABLE");
      }
   }

   private void ensureInProgress() {
      if (statusOrder != StatusOrder.IN_PROGRESS) {
         throw new IllegalStateException("SERVICE_ORDER_NOT_IN_PROGRESS");
      }
   }
}
