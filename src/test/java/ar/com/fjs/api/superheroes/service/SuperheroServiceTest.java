package ar.com.fjs.api.superheroes.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
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
	
	@Test
	void shouldThrowAnExceptionWhenHeroNotFounded() throws SuperheroNotFoundException {
		Throwable exception = assertThrows(SuperheroNotFoundException.class, () -> service.findById(999));
	    assertEquals("Not superhero founded with ID 999", exception.getMessage());
	}
	
	@Test
	void shouldReturnAllSuperheroesList() {
		List<Superhero> allHeroes = new ArrayList<>();
		allHeroes.add(new Superhero("Hero1", 100, 100, Gender.FEMALE, Specie.DROID));
		allHeroes.add(new Superhero("Hero2", 150, 80, Gender.MALE, Specie.HUMAN));
		allHeroes.add(new Superhero("Hero3", 110, 90, Gender.MALE, Specie.HUMAN));
		
		when(repository.findAll()).thenReturn(allHeroes);
		
		assertThat(service.findAll(""), hasSize(3));
	}
	
	@Test
	void shouldReturnOneSuperheroesInTheList() {
		List<Superhero> oneHero = new ArrayList<>();
		oneHero.add(new Superhero("Hero2", 150, 80, Gender.MALE, Specie.HUMAN));
		
		when(repository.findByNameContainingIgnoreCase("Hero2")).thenReturn(oneHero);
		
		assertThat(service.findAll("Hero2"), hasSize(1));
	}
	
	@Test
	void shouldUpdateAndReturnTheUpdatedHero() throws SuperheroNotFoundException {
		Superhero hero = new Superhero("Hero", 150, 80, Gender.MALE, Specie.HUMAN);
		hero.setId(1L);
		
		Optional<Superhero> originalHero = Optional.of(hero);
		Superhero modifiedHero = new Superhero("Hero modified", 220, 150, Gender.MALE, Specie.WOOKIE);
		modifiedHero.setId(1L);
		
		SuperheroDto modifiedHeroDto = new SuperheroDto("Hero modified", 220, 150, Gender.MALE, Specie.WOOKIE);
		
		when(repository.findById(1L)).thenReturn(originalHero);
		when(repository.saveAndFlush(modifiedHero)).thenReturn(modifiedHero);
		//doNothing().when(cacheManager).getCache("superheroes").evict(modifiedHero);
		
		SuperheroDto result = service.update(1L, modifiedHeroDto);
		
		assertEquals(result, modifiedHeroDto);
	}
	
	@Test
	void shouldThrowExceptionIfHeroIDToUpdateDoesNotExists() throws SuperheroNotFoundException {
		SuperheroDto modifiedHeroDto = new SuperheroDto("Hero modified", 220, 150, Gender.MALE, Specie.WOOKIE);
		
		when(repository.findById(999L)).thenReturn(Optional.empty());
		
		Throwable exception = assertThrows(SuperheroNotFoundException.class, () -> service.update(999L, modifiedHeroDto));
	    assertEquals("Not superhero founded with ID 999", exception.getMessage());
	}
	
	@Test
	void shouldCreateAndReturnANewSuperhero() {
		Superhero newHero = new Superhero("New Hero", 220, 150, Gender.MALE, Specie.WOOKIE);
		SuperheroDto newHeroDto = new SuperheroDto("New Hero", 220, 150, Gender.MALE, Specie.WOOKIE);
		
		when(repository.saveAndFlush(newHero)).thenReturn(newHero);
		
		assertEquals(service.create(newHeroDto), newHeroDto);
	}
}
