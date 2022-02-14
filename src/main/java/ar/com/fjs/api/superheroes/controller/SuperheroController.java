package ar.com.fjs.api.superheroes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.com.fjs.api.superheroes.aop.TrackExecutionTime;
import ar.com.fjs.api.superheroes.dto.DataResponseBody;
import ar.com.fjs.api.superheroes.dto.SuperheroDto;
import ar.com.fjs.api.superheroes.exception.SuperheroNotFoundException;
import ar.com.fjs.api.superheroes.service.SuperheroService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/superheroes")
@RequiredArgsConstructor
@Slf4j
public class SuperheroController {
	
	@Autowired
	private SuperheroService superheroService;

	@GetMapping("/{id}")
	@TrackExecutionTime
	public ResponseEntity<DataResponseBody> getAllSuperheroes(@PathVariable("id") long id) 
			throws SuperheroNotFoundException {
		
		log.info("Find superheroe by ID: {}", id);
		
		DataResponseBody body = new DataResponseBody();
		SuperheroDto superhero = superheroService.findById(id); 
		
		body.getData().add(superhero);
		
		return new ResponseEntity<>(body, HttpStatus.OK);
	}
	
	@GetMapping
	@TrackExecutionTime
	public ResponseEntity<DataResponseBody> getAllSuperheroes(
			@RequestParam(name = "name", required = false, defaultValue = "") String name) 
			throws SuperheroNotFoundException {
		
		log.info("Find all superheroes or by name containing: {}", name);
		
		DataResponseBody body = new DataResponseBody();
		List<SuperheroDto> superheroes = superheroService.findAll(name);
		
		body.setData(superheroes);
		
		return new ResponseEntity<>(body, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	@TrackExecutionTime
	public ResponseEntity<DataResponseBody> updateHero(@PathVariable("id") long id, 
			@RequestBody SuperheroDto superhero) throws SuperheroNotFoundException {
		log.info("Find superheroe by ID: {}", id);
		
		DataResponseBody body = new DataResponseBody();
		SuperheroDto modified = superheroService.update(id, superhero);
		
		body.getData().add(modified);
		
		return new ResponseEntity<>(body, HttpStatus.OK);
	}
	
	@PostMapping
	@TrackExecutionTime
	public ResponseEntity<DataResponseBody> createHero( 
			@RequestBody SuperheroDto superhero) throws SuperheroNotFoundException {
		log.info("Creating superheroe");
		
		DataResponseBody body = new DataResponseBody();
		SuperheroDto created = superheroService.create(superhero);
		
		body.getData().add(created);
		
		return new ResponseEntity<>(body, HttpStatus.CREATED);
	}
	
}
