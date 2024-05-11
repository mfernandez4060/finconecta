package finconecta.spring.cloud.msvc.products.Exceptions;

public class ProductUpdateErrorException extends RuntimeException {

	private static final String THE_TRANSACTION_COULD_NOT_BE_COMPLETED_PRODUCT_S = "The transaction could not be completed. Product [%s]";

	public ProductUpdateErrorException(Long id) {
		super(String.format(THE_TRANSACTION_COULD_NOT_BE_COMPLETED_PRODUCT_S, id));
	}

	private static final long serialVersionUID = -5262662173716565981L;

}
