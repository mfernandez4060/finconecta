package finconecta.spring.cloud.msvc.users.handlers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import finconecta.spring.cloud.msvc.users.dtos.ApiError;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {
	private static final String OPERATION_NOT_ALLOWED = "Operation not allowed";
	private static final String S_SHOULD_BE_OF_TYPE_S = "%s should be of type %s";
	private static final String EMPTY = "";

	// 500

	@ExceptionHandler({ RuntimeException.class })
	public ResponseEntity<?> handleAll(final Exception ex, final WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), ex.getMessage());
		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			WebRequest request) {
		String error = String.format(S_SHOULD_BE_OF_TYPE_S, ex.getName(), extracted(ex));

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, OPERATION_NOT_ALLOWED, OPERATION_NOT_ALLOWED);
		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	private String extracted(MethodArgumentTypeMismatchException ex) {
		return ex.getRequiredType() == null ? EMPTY : ex.getRequiredType().getName();
	}
}