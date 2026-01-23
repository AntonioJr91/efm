package afsj.efm.category.infrastructure.persistence;

import afsj.efm.category.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryJpaRepository extends JpaRepository<Category, Long> {
   Optional<Category> findByName(String name);
}
