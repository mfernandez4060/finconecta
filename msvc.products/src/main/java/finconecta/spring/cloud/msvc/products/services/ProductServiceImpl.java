package finconecta.spring.cloud.msvc.products.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import finconecta.spring.cloud.msvc.products.Exceptions.ProductAlradyExistsException;
import finconecta.spring.cloud.msvc.products.Exceptions.ProductCreateErrorException;
import finconecta.spring.cloud.msvc.products.Exceptions.ProductNotFoundException;
import finconecta.spring.cloud.msvc.products.Exceptions.ProductUpdateErrorException;
import finconecta.spring.cloud.msvc.products.dtos.ProductDto;
import finconecta.spring.cloud.msvc.products.models.entity.Product;
import finconecta.spring.cloud.msvc.products.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	private static final String NAME = "name";
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	@Transactional(readOnly = true)
	public List<Product> getAllProduts() {
		return (List<Product>) productRepository.findAll(Sort.by(NAME));
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Product> getProductById(Long id) {
		return productRepository.findById(id);
	}

	@Override
	public Product save(Product product) {
		try {
			return productRepository.save(product);
		} catch (DataIntegrityViolationException e) {
			throw new ProductAlradyExistsException(product.getCode());
		} catch (Exception e) {
			throw new ProductCreateErrorException(modelMapper.map(product, ProductDto.class));
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteById(Long id) {
		try {
			productRepository.deleteById(id);
		} catch (Exception e) {
			throw new ProductUpdateErrorException(id);
		}
	}

	@Override
	public Product update(Product product, Long id) {
		Optional<Product> productOptional = this.getProductById(id);
		if (productOptional.isPresent()) {

			Product productEntity = productOptional.get();
			if (product.getBarCode() != null && !product.getBarCode().isBlank())
				productEntity.setBarCode(product.getBarCode());
			if (product.getName() != null && !product.getName().isBlank())
				productEntity.setName(product.getName());
			if (product.getSerial() != null && !product.getSerial().isBlank())
				productEntity.setSerial(product.getSerial());
			if (product.getCode() != null && !product.getCode().isBlank())
				productEntity.setCode(product.getCode());

			try {
				return productRepository.save(productEntity);
			} catch (DataIntegrityViolationException e) {
				throw new ProductAlradyExistsException(product.getCode());				
			} catch (Exception e) {
				throw new ProductUpdateErrorException(id);
			}
		}

		throw new ProductNotFoundException(id);
	}
}
