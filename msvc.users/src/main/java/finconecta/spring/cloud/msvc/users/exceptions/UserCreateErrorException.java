package finconecta.spring.cloud.msvc.users.exceptions;

import finconecta.spring.cloud.msvc.users.dtos.UserDto;

public class UserCreateErrorException extends RuntimeException {

	private static final String THE_TRANSACTION_COULD_NOT_BE_COMPLETED_USER_S = "The transaction could not be completed. User [%s]";

	public UserCreateErrorException(UserDto user) {
		super(String.format(THE_TRANSACTION_COULD_NOT_BE_COMPLETED_USER_S, user));
	}

	private static final long serialVersionUID = -5262662173716565981L;

}
