package afsj.efm.production.application.usecases;

import afsj.efm.production.application.dtos.ProductionRequest;
import afsj.efm.production.application.dtos.ProductionResponse;
import afsj.efm.production.application.mappers.ProductionMapper;
import afsj.efm.production.infraestructure.persistence.ProductionJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateProductionUseCase {

   private final ProductionJpaRepository productionRepository;

   public CreateProductionUseCase(ProductionJpaRepository productionRepository) {
      this.productionRepository = productionRepository;
   }

   @Transactional
   public ProductionResponse execute(ProductionRequest request) {
      var production = productionRepository.save(ProductionMapper.toEntity(request));
      return ProductionMapper.toDto(production);
   }
}
