package ar.com.fjs.api.superheroes.service;

import java.util.List;

import ar.com.fjs.api.superheroes.dto.SuperheroDto;
import ar.com.fjs.api.superheroes.exception.SuperheroNotFoundException;

public interface SuperheroService {
	
	SuperheroDto findById(long id) throws SuperheroNotFoundException;
	
	SuperheroDto update(long id, SuperheroDto dto) throws SuperheroNotFoundException;
	
	SuperheroDto create(SuperheroDto dto);
	
	List<SuperheroDto> findAll(String name);
}
