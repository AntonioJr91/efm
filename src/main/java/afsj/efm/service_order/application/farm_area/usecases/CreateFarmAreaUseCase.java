package afsj.efm.service_order.application.farm_area.usecases;

import afsj.efm.service_order.application.farm_area.dtos.FarmAreaRequest;
import afsj.efm.service_order.application.farm_area.dtos.FarmAreaResponse;
import afsj.efm.service_order.application.farm_area.errors.FarmAreaConflict;
import afsj.efm.service_order.application.farm_area.mappers.FarmAreaMapper;
import afsj.efm.service_order.domain.entities.FarmArea;
import afsj.efm.service_order.infrastructure.persistence.FarmAreaJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateFarmAreaUseCase {

   private final FarmAreaJpaRepository farmAreaJpaRepository;

   public CreateFarmAreaUseCase(FarmAreaJpaRepository farmAreaJpaRepository) {
      this.farmAreaJpaRepository = farmAreaJpaRepository;
   }

   @Transactional
   public FarmAreaResponse execute(FarmAreaRequest request) {
      farmAreaJpaRepository.findByName(request.name())
              .ifPresent(farmArea -> {
                 throw FarmAreaConflict.farmAreaAlreadyExists(request.name());
              });

      var newFarmArea = new FarmArea(request.name());

      return FarmAreaMapper.toDto(farmAreaJpaRepository.save(newFarmArea));
   }
}
