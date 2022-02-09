package ar.com.fjs.api.superheroes.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class DataResponseBody {
	private List<SuperheroDto> data = new ArrayList<>();
}
