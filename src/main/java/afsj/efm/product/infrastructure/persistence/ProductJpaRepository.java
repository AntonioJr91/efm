package afsj.efm.product.infrastructure.persistence;

import afsj.efm.product.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductJpaRepository extends JpaRepository<Product, Long> {
   Optional<Product> findByName(String name);
}
