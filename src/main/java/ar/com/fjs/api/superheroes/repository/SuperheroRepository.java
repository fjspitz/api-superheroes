package ar.com.fjs.api.superheroes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.fjs.api.superheroes.model.Superhero;

@Repository
public interface SuperheroRepository extends JpaRepository<Superhero, Long> {
	
	List<Superhero> findByNameContainingIgnoreCase(String name);

}
