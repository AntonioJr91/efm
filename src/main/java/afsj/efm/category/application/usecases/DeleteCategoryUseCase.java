package afsj.efm.category.application.usecases;

import afsj.efm.category.application.errors.CategoryNotFound;
import afsj.efm.category.infrastructure.persistence.CategoryJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteCategoryUseCase {

   private final CategoryJpaRepository repository;

   public DeleteCategoryUseCase(CategoryJpaRepository repository) {
      this.repository = repository;
   }

   @Transactional
   public void execute(Long id) {
      if (!repository.existsById(id)) throw CategoryNotFound.byId();
      repository.deleteById(id);
   }
}
