package pet.store.controller.error;

import java.util.Collections;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalErrorHandler {

	@ExceptionHandler(NoSuchElementException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Map<String, String> handleNotFound(NoSuchElementException ex) {
		log.warn("Resource not found: {}", ex.getMessage());
		return Collections.singletonMap("message", ex.toString());
	}

	@ExceptionHandler(DuplicateKeyException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public Map<String, String> handleDuplicate(DuplicateKeyException ex) {
		log.warn("Duplicate key: {}", ex.getMessage());
		return Collections.singletonMap("message", ex.toString());
	}
}
