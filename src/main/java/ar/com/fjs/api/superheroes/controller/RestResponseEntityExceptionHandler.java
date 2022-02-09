package ar.com.fjs.api.superheroes.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import ar.com.fjs.api.superheroes.dto.ErrorResponseBody;
import ar.com.fjs.api.superheroes.exception.SuperheroNotFoundException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class, MethodArgumentTypeMismatchException.class })
	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
		ErrorResponseBody body = new ErrorResponseBody("Invalid input params/variables.", "");
		return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(value = { SuperheroNotFoundException.class })
	protected ResponseEntity<Object> handleNotFoundException(Exception ex, WebRequest request) {
		ErrorResponseBody body = new ErrorResponseBody("Superhero not founded.", ex.getMessage());
		return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
}
