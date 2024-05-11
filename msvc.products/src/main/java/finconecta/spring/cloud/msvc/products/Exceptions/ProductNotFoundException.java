package finconecta.spring.cloud.msvc.products.Exceptions;

public class ProductNotFoundException extends RuntimeException {
	private static final String PRODUCT_NOT_FOUND_PRODUCT_ID_S = "Product not found, product id [%s]";

	public ProductNotFoundException(Long id) {
		super(String.format(PRODUCT_NOT_FOUND_PRODUCT_ID_S, id));
	}

	private static final long serialVersionUID = -5262662173716565987L;

}
