package afsj.efm.production.application.usecases;

import afsj.efm.production.application.dtos.ProductionResponse;
import afsj.efm.production.application.mappers.ProductionMapper;
import afsj.efm.production.infraestructure.persistence.ProductionJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ListProductionsUseCase {

   private final ProductionJpaRepository productionRepository;

   public ListProductionsUseCase(ProductionJpaRepository productionRepository) {
      this.productionRepository = productionRepository;
   }

   @Transactional(readOnly = true)
   public List<ProductionResponse> execute() {
      return ProductionMapper.toDto(productionRepository.findAll());
   }
}
