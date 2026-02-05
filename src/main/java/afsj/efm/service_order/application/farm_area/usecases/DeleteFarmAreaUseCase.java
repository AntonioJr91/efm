package afsj.efm.service_order.application.farm_area.usecases;

import afsj.efm.service_order.infrastructure.persistence.FarmAreaJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteFarmAreaUseCase {

   private final FarmAreaJpaRepository farmAreaJpaRepository;

   public DeleteFarmAreaUseCase(FarmAreaJpaRepository farmAreaJpaRepository) {
      this.farmAreaJpaRepository = farmAreaJpaRepository;
   }

   @Transactional
   public void execute(Long id) {
      if (!farmAreaJpaRepository.existsById(id)) throw new IllegalArgumentException();
      farmAreaJpaRepository.deleteById(id);
   }
}
