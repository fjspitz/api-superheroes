package ar.com.fjs.api.superheroes.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.com.fjs.api.superheroes.dto.SuperheroDto;
import ar.com.fjs.api.superheroes.exception.SuperheroNotFoundException;
import ar.com.fjs.api.superheroes.model.Gender;
import ar.com.fjs.api.superheroes.model.Specie;
import ar.com.fjs.api.superheroes.model.Superhero;
import ar.com.fjs.api.superheroes.repository.SuperheroRepository;
import ar.com.fjs.api.superheroes.service.impl.SuperheroServiceImpl;

@ExtendWith(MockitoExtension.class)
class SuperheroServiceTest {

	@Mock
	private SuperheroRepository repository;
	
	private SuperheroService service;
	
	@BeforeEach
	void init() {
		service = new SuperheroServiceImpl(repository);
	}
	
	@Test
	void shouldReturnAFoundedSuperhero() throws SuperheroNotFoundException {
		Superhero luke = new Superhero("Luke Skywalker", 172, 77, Gender.MALE, Specie.HUMAN);
		Optional<Superhero> founded = Optional.of(luke);
		
		when(repository.findById(1L)).thenReturn(founded);
		
		SuperheroDto foundedDto = service.findById(1L);
		
		assertThat(foundedDto.getName()).isNotNull();
	}
}
