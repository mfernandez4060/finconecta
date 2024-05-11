package finconecta.spring.cloud.msvc.users.exceptions;

public class UserNotFoundException extends RuntimeException {
	private static final String USER_NOT_FOUND_PRODUCT_ID_S = "User not found, user id [%s]";

	public UserNotFoundException(String id) {
		super(String.format(USER_NOT_FOUND_PRODUCT_ID_S, id));
	}

	private static final long serialVersionUID = -5262662173716565987L;

}
