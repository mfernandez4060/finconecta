package finconecta.spring.cloud.msvc.products.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import finconecta.spring.cloud.msvc.products.dtos.CreateProductDto;
import finconecta.spring.cloud.msvc.products.dtos.ProductDto;
import finconecta.spring.cloud.msvc.products.models.entity.Product;
import finconecta.spring.cloud.msvc.products.services.CommonService;
import finconecta.spring.cloud.msvc.products.services.ProductService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

	private static final String VERSION = "/version";
	private static final String PRODUCTS_ID = "/{id}";
	@Autowired
	private ProductService productService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping(VERSION)
	public ResponseEntity<?> getVersion() {
		return ResponseEntity.ok(commonService.getVersion());
	}

	@GetMapping()
	public ResponseEntity<List<ProductDto>> getAllProduct() {
		List<ProductDto> dto = productService.getAllProduts().stream().map(p -> modelMapper.map(p, ProductDto.class))
				.collect(Collectors.toList());
		return ResponseEntity.ok(dto);

	}

	@GetMapping(PRODUCTS_ID)
	public ResponseEntity<?> getProduct(@PathVariable Long id) {
		Optional<Product> productOptional = productService.getProductById(id);
		if (productOptional.isPresent()) {
			return ResponseEntity.ok(modelMapper.map(productOptional.get(), ProductDto.class));
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping()
	public ResponseEntity<?> add(@Valid @RequestBody CreateProductDto product, BindingResult result) {
		if (result.hasErrors()) {
			return validate(result);
		}

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(modelMapper.map(productService.save(modelMapper.map(product, Product.class)), ProductDto.class));
	}

	@PutMapping(PRODUCTS_ID)
	public ResponseEntity<?> update(@Valid @RequestBody ProductDto product, BindingResult result,
			@PathVariable Long id) {
		if (result.hasErrors()) {
			return validate(result);
		}

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(
				modelMapper.map(productService.update(modelMapper.map(product, Product.class), id), ProductDto.class));
	}

	@DeleteMapping(PRODUCTS_ID)
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Optional<Product> productOptional = productService.getProductById(id);
		if (productOptional.isPresent()) {

			productService.deleteById(id);
			return ResponseEntity.noContent().build();

		}

		return ResponseEntity.notFound().build();
	}
	
	private static ResponseEntity<Map<String, String>> validate(BindingResult result) {
		Map<String, String> errors = new HashMap<String, String>();
		result.getFieldErrors().forEach(err -> {
			errors.put(err.getField(), err.getField() + " " + err.getDefaultMessage());
		});
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
}