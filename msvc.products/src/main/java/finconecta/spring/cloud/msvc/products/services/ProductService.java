package finconecta.spring.cloud.msvc.products.services;

import java.util.List;
import java.util.Optional;

import finconecta.spring.cloud.msvc.products.models.entity.Product;

public interface ProductService {
        List<Product> getAllProduts();
        
        Optional<Product> getProductById(Long id);
        
        Product save(Product product);
        
        Product update(Product product, Long id);
        
        void deleteById(Long id);
 }
