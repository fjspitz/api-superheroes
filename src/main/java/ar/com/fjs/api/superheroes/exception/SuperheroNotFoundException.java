package ar.com.fjs.api.superheroes.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class SuperheroNotFoundException extends Exception {
	private static final long serialVersionUID = -5660314772295419411L;
	private final String message;
}
