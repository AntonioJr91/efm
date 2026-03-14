package afsj.efm.service_order.application.farm_area.usecases;

import afsj.efm.service_order.application.farm_area.dtos.FarmAreaResponse;
import afsj.efm.service_order.application.farm_area.errors.FarmAreaNotFound;
import afsj.efm.service_order.application.farm_area.mappers.FarmAreaMapper;
import afsj.efm.service_order.infrastructure.persistence.FarmAreaJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetFarmAreaByIdUseCase {

   private final FarmAreaJpaRepository farmAreaJpaRepository;

   public GetFarmAreaByIdUseCase(FarmAreaJpaRepository farmAreaJpaRepository) {
      this.farmAreaJpaRepository = farmAreaJpaRepository;
   }

   @Transactional(readOnly = true)
   public FarmAreaResponse execute(Long id) {
      return FarmAreaMapper.toDto(farmAreaJpaRepository.findById(id)
              .orElseThrow(FarmAreaNotFound::byId));
   }
}
