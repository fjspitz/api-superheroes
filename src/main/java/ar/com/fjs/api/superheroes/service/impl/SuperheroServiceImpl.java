package ar.com.fjs.api.superheroes.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.fjs.api.superheroes.dto.SuperheroDto;
import ar.com.fjs.api.superheroes.exception.SuperheroNotFoundException;
import ar.com.fjs.api.superheroes.model.Superhero;
import ar.com.fjs.api.superheroes.repository.SuperheroRepository;
import ar.com.fjs.api.superheroes.service.SuperheroService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SuperheroServiceImpl implements SuperheroService {

	@Autowired
	private final SuperheroRepository superheroRepository;
	
	@Cacheable("superheroes")
	@Transactional(readOnly = true)
	@Override
	public SuperheroDto findById(long id) throws SuperheroNotFoundException {
		
		Optional<Superhero> superhero = superheroRepository.findById(id);
		SuperheroDto dto = null;
		
		if (superhero.isPresent()) {
			dto = new SuperheroDto();
			dto.setName(superhero.get().getName());
			dto.setHeight(superhero.get().getHeight());
			dto.setMass(superhero.get().getMass());
			dto.setGender(superhero.get().getGender());
			dto.setSpecie(superhero.get().getSpecie());
		} else {
			throw new SuperheroNotFoundException(String.format("Not superhero founded with ID %d", id));
		}
		
		return dto;
	}

	@Cacheable(value = "superheroes", unless = "#name != ''")
	@Transactional(readOnly = true)
	@Override
	public List<SuperheroDto> findAll(String name) {
		
		List<Superhero> heroesList;
		
		if (!name.isBlank()) {
			log.info("findAll by name: {}", name);
			heroesList = superheroRepository.findByNameContainingIgnoreCase(name);
		} else {
			log.info("findAll");
			heroesList = superheroRepository.findAll();
		}
		
		List<SuperheroDto> resultList = new ArrayList<>(); 
				
		heroesList.forEach(h -> {
			SuperheroDto dto = new SuperheroDto(h.getName(), h.getHeight(), h.getMass(), h.getGender(), h.getSpecie());
			resultList.add(dto);
		});
		
		return resultList;
	}

	@Transactional
	@Override
	public SuperheroDto update(long id, SuperheroDto dto) throws SuperheroNotFoundException {
		
		Optional<Superhero> superhero = superheroRepository.findById(id);
		
		if (!superhero.isPresent()) {
			throw new SuperheroNotFoundException(String.format("Not superhero founded with ID %d", id));
		}
		
		Superhero modified = superhero.get();
		modified.setId(id);
		modified.setName(dto.getName());
		modified.setHeight(dto.getHeight());
		modified.setMass(dto.getMass());
		modified.setGender(dto.getGender());
		modified.setSpecie(dto.getSpecie());
		
		modified = superheroRepository.saveAndFlush(modified);
			
		return new SuperheroDto(modified.getName(), modified.getHeight(), modified.getMass(), modified.getGender(), modified.getSpecie());
	}

	@Transactional
	@Override
	public SuperheroDto create(SuperheroDto dto) {
		Superhero created = new Superhero();
		
		created.setName(dto.getName());
		created.setHeight(dto.getHeight());
		created.setMass(dto.getMass());
		created.setGender(dto.getGender());
		created.setSpecie(dto.getSpecie());
		
		superheroRepository.saveAndFlush(created);
		
		return dto;
	}
}
