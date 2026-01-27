package afsj.efm.product.application.service;

import afsj.efm.product.application.dtos.ProductRequest;
import afsj.efm.product.application.dtos.ProductResponse;
import afsj.efm.product.domain.entities.Product;
import afsj.efm.product.domain.enums.UnitOfMeasure;
import afsj.efm.product.domain.exceptions.InsufficientStockException;
import afsj.efm.product.domain.exceptions.InvalidMovementQuantityException;
import afsj.efm.product.infrastructure.persistence.ProductJpaRepository;
import afsj.efm.shared.application.exceptions.ConflictException;
import afsj.efm.shared.application.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductApplicationServiceTest {

   Product milho;
   @Mock
   private ProductJpaRepository repository;
   @InjectMocks
   private ProductApplicationService service;

   @BeforeEach
   void setUp() {
      milho = new Product("milho", 15, UnitOfMeasure.UNIT);
   }

   @Test
   @DisplayName("Should return empty list when no products exist")
   void returnEmptyList() {
      when(repository.findAll()).thenReturn(List.of());

      var result = service.listProducts();

      assertThat(result).isEmpty();
      verify(repository).findAll();
   }

   @Test
   @DisplayName("Should return a product list")
   void returnProductList() {
      var products = List.of(milho);

      when(repository.findAll()).thenReturn(products);

      var result = service.listProducts();

      assertThat(result).hasSize(1)
              .extracting(ProductResponse::name)
              .containsExactly("milho");

   }

   @Test
   @DisplayName("Should return product when found by id")
   void returnProductById() {
      when(repository.findById(milho.getId())).thenReturn(Optional.of(milho));

      var result = service.findById(milho.getId());

      assertThat(result.name()).isEqualTo(milho.getName());
      verify(repository).findById(milho.getId());
   }

   @Test
   @DisplayName("Should throw not found when product does not exist")
   void returnNotFound() {
      when(repository.findById(milho.getId())).thenReturn(Optional.empty());

      assertThrows(ResourceNotFoundException.class,
              () -> service.findById(milho.getId()));
   }

   @Test
   @DisplayName("Should save product when name does not exist")
   void saveProduct() {
      var request = new ProductRequest(milho.getName(), milho.getAvailableStock(), milho.getUnitOfMeasure());

      when(repository.findByName(milho.getName()))
              .thenReturn(Optional.empty());

      when(repository.save(any(Product.class)))
              .thenAnswer(invocation -> invocation.getArgument(0));

      var result = service.save(request);

      assertThat(result.name()).isEqualTo(request.name());
      verify(repository).save(any(Product.class));
   }

   @Test
   @DisplayName("Should throw conflict when product name already exists")
   void nameConflict() {
      var request = new ProductRequest(milho.getName(), milho.getAvailableStock(), milho.getUnitOfMeasure());

      when(repository.findByName(milho.getName()))
              .thenReturn(Optional.of(new Product(milho.getName(), milho.getAvailableStock(), milho.getUnitOfMeasure())));

      assertThrows(ConflictException.class,
              () -> service.save(request));
      verify(repository, never()).save(any());
   }

   @Test
   @DisplayName("Should delete product when exists")
   void deleteProduct() {
      when(repository.existsById(1L)).thenReturn(true);

      service.delete(1L);
      verify(repository).deleteById(1L);
   }

   @Test
   @DisplayName("Should throw not found when deleting  non existing product")
   void returnErrorWhenDeleteProduct() {
      when(repository.existsById(1L)).thenReturn(false);

      assertThrows(ResourceNotFoundException.class, () -> service.delete(1L));
      verify(repository, never()).deleteById(1L);
   }

   @Test
   @DisplayName("Should increase product stock when valid quantity is provided ")
   void increaseStock() {
      int initialStock = milho.getAvailableStock();

      when(repository.findById(anyLong()))
              .thenReturn(Optional.of(milho));

      var response = service.increase(1L, 5);

      assertThat(response.quantity()).isEqualTo(initialStock + 5);
      verify(repository).findById(anyLong());
   }

   @Test
   @DisplayName("Should throw exception when increasing stock with invalid quantity")
   void increaseWithInvalidQuantity() {
      when(repository.findById(anyLong()))
              .thenReturn(Optional.of(milho));
      assertThrows(InvalidMovementQuantityException.class,
              () -> service.increase(1L, 0));
      verify(repository, never()).save(any());
   }

   @Test
   @DisplayName("Should throw not found when increasing stock of non existing product")
   void increaseStockProductNotFound() {
      when(repository.findById(anyLong()))
              .thenReturn(Optional.empty());
      assertThrows(ResourceNotFoundException.class,
              () -> service.increase(1L, 0));
   }

   @Test
   @DisplayName("Should decrease product stock when valid quantity is provided ")
   void decreaseStock() {
      int initialStock = milho.getAvailableStock();

      when(repository.findById(anyLong()))
              .thenReturn(Optional.of(milho));

      var response = service.decrease(1L, 5);

      assertThat(response.quantity()).isEqualTo(initialStock - 5);
      verify(repository).findById(1L);
   }

   @Test
   @DisplayName("Should throw exception when decreasing stock below zero")
   void decreaseStockInsufficient() {
      when(repository.findById(anyLong()))
              .thenReturn(Optional.of(milho));

      assertThrows(InsufficientStockException.class,
              () -> service.decrease(1L, 100));
      verify(repository, never()).save(any());
   }

   @Test
   @DisplayName("Should throw not found when decreasing stock of non existing product")
   void decreaseStockProductNotFound() {
      when(repository.findById(anyLong()))
              .thenReturn(Optional.empty());

      assertThrows(ResourceNotFoundException.class,
              () -> service.decrease(1L, 5));
      verify(repository, never()).save(any());
   }


}