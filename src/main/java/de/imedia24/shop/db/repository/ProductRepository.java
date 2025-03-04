package de.imedia24.shop.db.repository;

import de.imedia24.shop.db.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, String> {
    Optional<ProductEntity> findBySku(String sku);
    List<ProductEntity> findBySkuIn(List<String> skus);
    boolean existsBySku(String sku);
}
