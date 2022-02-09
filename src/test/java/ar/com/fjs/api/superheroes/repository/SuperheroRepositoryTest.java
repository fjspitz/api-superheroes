package ar.com.fjs.api.superheroes.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import ar.com.fjs.api.superheroes.model.Gender;
import ar.com.fjs.api.superheroes.model.Specie;
import ar.com.fjs.api.superheroes.model.Superhero;

@DataJpaTest
class SuperheroRepositoryTest {

	@Autowired
	private SuperheroRepository superheroRepository;
	
	@BeforeEach
	void init() {
		List<Superhero> heroes = new ArrayList<>();
		
		heroes.add(new Superhero("Luke Skywalker", 172, 77, Gender.MALE, Specie.HUMAN));
	}
	
	@AfterEach
    public void destroyAll(){
		superheroRepository.deleteAll();
    }
	
	@Test
    void findAll_success() {
        List<Superhero> allCustomer = superheroRepository.findAll();
        assertThat(allCustomer.size()).isPositive();
    }
}
