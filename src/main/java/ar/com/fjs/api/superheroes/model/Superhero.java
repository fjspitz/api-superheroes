package ar.com.fjs.api.superheroes.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "super_heroes")
@Data
@NoArgsConstructor
public class Superhero {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String name;
	private int height;
	private int mass;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	@Enumerated(EnumType.STRING)
	private Specie specie;
	
	public Superhero(String name, int height, int mass, Gender gender, Specie specie) {
		this.name = name;
		this.height = height;
		this.mass = mass;
		this.gender = gender;
		this.specie = specie;
	}
}
