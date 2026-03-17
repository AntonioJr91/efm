package afsj.efm.production.application.usecases;

import afsj.efm.production.application.dtos.ProductionResponse;
import afsj.efm.production.application.errors.ProductionNotFound;
import afsj.efm.production.application.mappers.ProductionMapper;
import afsj.efm.production.infraestructure.persistence.ProductionJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetProductionByIdUseCase {

   private final ProductionJpaRepository productionRepository;

   public GetProductionByIdUseCase(ProductionJpaRepository productionRepository) {
      this.productionRepository = productionRepository;
   }

   @Transactional(readOnly = true)
   public ProductionResponse execute(Long id) {
      var production = productionRepository.findById(id).orElseThrow(ProductionNotFound::byId);
      return ProductionMapper.toDto(production);
   }
}
