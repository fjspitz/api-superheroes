package ar.com.fjs.api.superheroes.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ar.com.fjs.api.superheroes.dto.SuperheroDto;
import ar.com.fjs.api.superheroes.exception.SuperheroNotFoundException;
import ar.com.fjs.api.superheroes.model.Gender;
import ar.com.fjs.api.superheroes.model.Specie;
import ar.com.fjs.api.superheroes.service.SuperheroService;

@DisplayName("Web layer testing")
@WebMvcTest
class SuperheroControllerTest {
	
	@Autowired
	private ObjectMapper mapper;

	@Autowired
    private MockMvc mvc;
	
	@MockBean
    private SuperheroService service;
	
	@DisplayName("Single response tests")
	@Nested
	class SingleResponse {
	
		@DisplayName("Should return 200 and Luke Skywalker hero when searched by ID")
		@Test
		void shouldReturn200AndLukeSkywalkerHeroWhenSearchedByID() throws Exception {
			
			SuperheroDto luke = new SuperheroDto("Luke Skywalker", 172, 77, Gender.MALE, Specie.HUMAN);
			
			when(service.findById(1L)).thenReturn(luke);
			
			mvc.perform(get("/api/superheroes/1")
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data", hasSize(1)))
				.andExpect(jsonPath("$.data[0].name", is(luke.getName())))
				.andExpect(jsonPath("$.data[0].height", is(luke.getHeight())))
				.andExpect(jsonPath("$.data[0].mass", is(luke.getMass())))
				.andExpect(jsonPath("$.data[0].gender").value("MALE"))
				.andExpect(jsonPath("$.data[0].specie").value("HUMAN"));
		}
		
		@DisplayName("Should return 404 if the hero searched does not exists")
		@Test
		void shouldReturn404IfHeroSearchedDoesNotExists() throws Exception {
			when(service.findById(99L)).thenThrow(SuperheroNotFoundException.class);
			
		    mvc.perform(get("/api/superheroes/99")
		    		.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
		}
		
		@DisplayName("Should return 400 if ID or name parameter for the search is not valid")
		@Test
		void shouldReturn400IfParametersAreInvalidOrIllegal() throws Exception {
		    mvc.perform(get("/api/superheroes/xxx")
		    		.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
		}
		
		@DisplayName("Should return 200 when hero is updated")
		@Test
		void shouldReturn200WhenHeroIsUpdated() throws Exception {
			
			SuperheroDto modified = new SuperheroDto("Han Solo", 180, 80, Gender.FEMALE, Specie.HUMAN);
			
			when(service.update(1L, modified)).thenReturn(modified);
			
			mvc.perform(put("/api/superheroes/1")
					.content(asJsonString(new SuperheroDto("Han Solo", 180, 80, Gender.FEMALE, Specie.HUMAN)))
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data", hasSize(1)))
				.andExpect(jsonPath("$.data[0].name").value("Han Solo"))
				.andExpect(jsonPath("$.data[0].height").value(180))
				.andExpect(jsonPath("$.data[0].mass").value(80))
				.andExpect(jsonPath("$.data[0].gender").value("FEMALE"))
				.andExpect(jsonPath("$.data[0].specie").value("HUMAN"));
		}
		
		@DisplayName("Should return 201 when hero is created")
		@Test
		void shouldReturn201WhenHeroIsCreated() throws Exception {
			SuperheroDto created = new SuperheroDto("Boba Fett", 183, 78, Gender.MALE, Specie.HUMAN);
			
			when(service.create(created)).thenReturn(created);
			
			mvc.perform(post("/api/superheroes")
					.content(asJsonString(new SuperheroDto("Boba Fett", 183, 78, Gender.MALE, Specie.HUMAN)))
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.data", hasSize(1)))
				.andExpect(jsonPath("$.data[0].name").value("Boba Fett"))
				.andExpect(jsonPath("$.data[0].height").value(183))
				.andExpect(jsonPath("$.data[0].mass").value(78))
				.andExpect(jsonPath("$.data[0].gender").value("MALE"))
				.andExpect(jsonPath("$.data[0].specie").value("HUMAN"));
		}
	}
	
	private String asJsonString(final Object obj) {
	    try {
	        return mapper.writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

	@DisplayName("Multiple response tests")
	@Nested
	class MultipleResponse {
		
		@DisplayName("Should return 200 and two heroes when searched by 'Skywalker'")
		@Test
		void shouldReturnTwoHeroesWhenNamesMatch() throws Exception {
			SuperheroDto luke = new SuperheroDto("Luke Skywalker", 172, 77, Gender.MALE, Specie.HUMAN);
			SuperheroDto darthVader = new SuperheroDto("Anakin Skywalker", 202, 136, Gender.MALE, Specie.HUMAN);
			List<SuperheroDto> heroes = new ArrayList<>();
			heroes.add(luke);
			heroes.add(darthVader);
			
			when(service.findAll("Skywalker")).thenReturn(heroes);
			
			mvc.perform(get("/api/superheroes?name=Skywalker")
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data", hasSize(2)))
				.andExpect(jsonPath("$.data[0].name", is(heroes.get(0).getName())))
				.andExpect(jsonPath("$.data[0].height", is(heroes.get(0).getHeight())))
				.andExpect(jsonPath("$.data[0].mass", is(heroes.get(0).getMass())))
				.andExpect(jsonPath("$.data[0].gender").value("MALE"))
				.andExpect(jsonPath("$.data[0].specie").value("HUMAN"))
				.andExpect(jsonPath("$.data[1].name", is(heroes.get(1).getName())))
				.andExpect(jsonPath("$.data[1].height", is(heroes.get(1).getHeight())))
				.andExpect(jsonPath("$.data[1].mass", is(heroes.get(1).getMass())))
				.andExpect(jsonPath("$.data[1].gender").value("MALE"))
				.andExpect(jsonPath("$.data[1].specie").value("HUMAN"));
		}
		
		@DisplayName("Should return 200 and an empty list if search was unsatisfactory")
		@Test
		void shouldReturn200AndAnEmptyListWhenNotFound() throws Exception {
			when(service.findAll("Skywalker")).thenReturn(Collections.emptyList());
			
			mvc.perform(get("/api/superheroes?name=Skywalker")
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data", hasSize(0)));
		}
	}
}
