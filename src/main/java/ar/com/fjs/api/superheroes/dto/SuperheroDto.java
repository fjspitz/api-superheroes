package ar.com.fjs.api.superheroes.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import ar.com.fjs.api.superheroes.model.Gender;
import ar.com.fjs.api.superheroes.model.Specie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonPropertyOrder(value = {"name", "height", "mass", "gender", "specie"})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuperheroDto {
	private String name;
	private int height;
	private int mass;
	private Gender gender;
	private Specie specie;
}
