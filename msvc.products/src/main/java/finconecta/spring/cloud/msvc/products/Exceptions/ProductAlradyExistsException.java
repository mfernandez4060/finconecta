package finconecta.spring.cloud.msvc.products.Exceptions;

public class ProductAlradyExistsException extends RuntimeException {


	public ProductAlradyExistsException(String code) {
		super(String.format("Product already exists, code %s", code));
	}

	private static final long serialVersionUID = -5262662173716565931L;

}
