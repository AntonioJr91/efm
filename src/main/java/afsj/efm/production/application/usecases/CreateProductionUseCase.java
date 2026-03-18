package afsj.efm.production.application.usecases;

import afsj.efm.product.application.errors.ProductNotFound;
import afsj.efm.product.infrastructure.persistence.ProductJpaRepository;
import afsj.efm.production.application.dtos.ProductionRequest;
import afsj.efm.production.application.dtos.ProductionResponse;
import afsj.efm.production.application.mappers.ProductionMapper;
import afsj.efm.production.infraestructure.persistence.ProductionJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateProductionUseCase {

   private final ProductionJpaRepository productionRepository;
   private final ProductJpaRepository productRepository;

   public CreateProductionUseCase(
           ProductionJpaRepository productionRepository,
           ProductJpaRepository productRepository
   ) {
      this.productionRepository = productionRepository;
      this.productRepository = productRepository;
   }

   @Transactional
   public ProductionResponse execute(ProductionRequest request) {
      var product = productRepository.findById(request.productId())
              .orElseThrow((ProductNotFound::byId));
      product.increaseStock(request.quantity());
      var production = productionRepository.save(ProductionMapper.toEntity(request));
      return ProductionMapper.toDto(production);
   }
}
