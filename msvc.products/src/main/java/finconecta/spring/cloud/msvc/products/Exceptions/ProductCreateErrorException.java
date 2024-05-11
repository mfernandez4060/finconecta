package finconecta.spring.cloud.msvc.products.Exceptions;

import finconecta.spring.cloud.msvc.products.dtos.ProductDto;

public class ProductCreateErrorException extends RuntimeException {

	private static final String THE_TRANSACTION_COULD_NOT_BE_COMPLETED_PRODUCT_S = "The transaction could not be completed. Product [%s]";

	public ProductCreateErrorException(ProductDto product) {
		super(String.format(THE_TRANSACTION_COULD_NOT_BE_COMPLETED_PRODUCT_S, product));
	}

	private static final long serialVersionUID = -5262662173716565981L;

}
