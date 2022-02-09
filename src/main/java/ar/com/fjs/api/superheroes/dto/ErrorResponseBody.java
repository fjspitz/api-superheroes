package ar.com.fjs.api.superheroes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorResponseBody {
	private String message;
	private String details;
}
