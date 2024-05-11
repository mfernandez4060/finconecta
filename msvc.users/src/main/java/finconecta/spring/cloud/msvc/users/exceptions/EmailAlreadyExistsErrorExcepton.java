package finconecta.spring.cloud.msvc.users.exceptions;

public class EmailAlreadyExistsErrorExcepton extends RuntimeException {

	private static final String EMAIL_ALREADY_EXISTS_S = "Email already exists %s";


	public EmailAlreadyExistsErrorExcepton(String email) {
		super(String.format(EMAIL_ALREADY_EXISTS_S, email));
	}


	private static final long serialVersionUID = -5262662173716565981L;

}
