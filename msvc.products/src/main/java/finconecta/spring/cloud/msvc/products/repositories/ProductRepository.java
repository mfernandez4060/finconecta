package finconecta.spring.cloud.msvc.products.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import finconecta.spring.cloud.msvc.products.models.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
