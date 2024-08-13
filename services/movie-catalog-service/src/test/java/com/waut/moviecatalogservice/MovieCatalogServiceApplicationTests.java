package com.waut.moviecatalogservice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waut.moviecatalogservice.dto.GenreRequest;
import com.waut.moviecatalogservice.dto.GenreResponse;
import com.waut.moviecatalogservice.dto.MovieRequest;
import com.waut.moviecatalogservice.dto.MovieResponse;
import com.waut.moviecatalogservice.model.Genre;
import com.waut.moviecatalogservice.model.Movie;
import com.waut.moviecatalogservice.repository.GenreRepository;
import com.waut.moviecatalogservice.repository.MovieRepository;
import junit.framework.AssertionFailedError;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.MethodOrderer.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class MovieCatalogServiceApplicationTests {

	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private GenreRepository genreRepository;

	private static String sharedMovieId;

	private static String sharedGenreId;

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Test
	@Order(1)
	void shouldCreateGenre() throws Exception {
		GenreRequest genreRequest = getGenreRequest();

//		Convert the genreRequest object to a JSON string
		String genreRequestString = objectMapper.writeValueAsString(genreRequest);

//		Perform a POST request to the /api/v1/catalog/genres endpoint
		MvcResult response = mockMvc.perform(post("/api/v1/catalog/genres").contentType(APPLICATION_JSON)
				.content(genreRequestString)
		).andExpect(status().isCreated()).andReturn();

//		Get the genre id from the response
		sharedGenreId = response.getResponse().getContentAsString();

//		Assert that the genre was created successfully
		Assertions.assertEquals(1, genreRepository.findAll().size());

//		Get the genre from the database
		Genre genre = genreRepository.findById(sharedGenreId).orElseThrow(() -> new AssertionFailedError("Genre not found"));

//		Assert that the genre has the correct values
		Assertions.assertEquals(genreRequest.name(), genre.getName());
		Assertions.assertEquals(genreRequest.description(), genre.getDescription());
		Assertions.assertEquals(genreRequest.imageUrl(), genre.getImageUrl());
		Assertions.assertEquals(genre.getCreatedAt(), genre.getUpdatedAt());
	}

	@Test
	@Order(2)
	void shouldGetGenreById() throws Exception {
		MvcResult response = mockMvc.perform(get("/api/v1/catalog/genres/" + sharedGenreId).contentType(APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

		GenreResponse genreResponse = objectMapper.readValue(response.getResponse().getContentAsString(), new GenreTypeReference());

		GenreRequest genreRequest = getGenreRequest();

		Assertions.assertEquals(1, genreRepository.findAll().size());
		Assertions.assertEquals(sharedGenreId, genreResponse.id());

		Assertions.assertEquals(genreRequest.name(), genreResponse.name());
		Assertions.assertEquals(genreRequest.description(), genreResponse.description());
		Assertions.assertEquals(genreRequest.imageUrl(), genreResponse.imageUrl());
	}

	@Test
	@Order(3)
	void shouldGetAllGenres() throws Exception {
		MvcResult response = mockMvc.perform(get("/api/v1/catalog/genres").contentType(APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

		List<GenreResponse> genreResponses = objectMapper.readValue(response.getResponse().getContentAsString(), new GenreListTypeReference());

		Assertions.assertEquals(1, genreResponses.size());
	}

	@Test
	@Order(4)
	void shouldUpdateGenreById() throws Exception {
		GenreRequest genreRequest = getGenreRequestTwo();

//		Convert the genreRequest object to a JSON string
		String genreRequestString = objectMapper.writeValueAsString(genreRequest);

//		Perform a PUT request to the /api/v1/catalog/genres/{id} endpoint
		mockMvc.perform(put("/api/v1/catalog/genres/" + sharedGenreId).contentType(APPLICATION_JSON)
				.content(genreRequestString)
		).andExpect(status().isOk());

//		Get the genre from the database
		Genre genre = genreRepository.findById(sharedGenreId).orElseThrow(() -> new AssertionFailedError("Genre not found"));

//		Assert that the genre has the correct values
		Assertions.assertEquals(1, genreRepository.findAll().size());
		Assertions.assertEquals(sharedGenreId, genre.getId());

//		Assert that the genre has the correct values
		Assertions.assertEquals(genreRequest.name(), genre.getName());
		Assertions.assertEquals(genreRequest.description(), genre.getDescription());
		Assertions.assertEquals(genreRequest.imageUrl(), genre.getImageUrl());
	}

	@Test
	@Order(5)
	void shouldCreateMovie() throws Exception {
		MovieRequest movieRequest = getMovieRequest();

//		Convert the movieRequest object to a JSON string
		String movieRequestString = objectMapper.writeValueAsString(movieRequest);

//		Perform a POST request to the /api/v1/catalog/movies endpoint
		MvcResult response = mockMvc.perform(post("/api/v1/catalog/movies").contentType(APPLICATION_JSON)
				.content(movieRequestString)
		).andExpect(status().isCreated()).andReturn();

//		Get the movie id from the response
		sharedMovieId = response.getResponse().getContentAsString();

//		Assert that the movie was created successfully
		Assertions.assertEquals(1, movieRepository.findAll().size());

//		Get the movie from the database
		Movie movie = movieRepository.findById(sharedMovieId).orElseThrow(() -> new AssertionFailedError("Movie not found"));

//		Assert that the movie has the correct values
		Assertions.assertEquals(movieRequest.title(), movie.getTitle());
		Assertions.assertEquals(movieRequest.description(), movie.getDescription());
		Assertions.assertEquals(movieRequest.imageUrl(), movie.getImageUrl());
		Assertions.assertEquals(movieRequest.cast(), movie.getCast());
		Assertions.assertEquals(movieRequest.director(), movie.getDirector());
//		Assertions.assertEquals(movieRequest.releaseDate(), movie.getReleaseDate());
		Assertions.assertEquals(movieRequest.durationMinutes(), movie.getDurationMinutes());
		Assertions.assertEquals(movieRequest.genres().size(), movie.getGenres().size());
		Assertions.assertEquals(movie.getCreatedAt(), movie.getUpdatedAt());
	}

	@Test
	@Order(6)
	void shouldGetMovieById() throws Exception {
//		Perform a GET request to the /api/v1/catalog/movies/{id} endpoint
		MvcResult response = mockMvc.perform(get("/api/v1/catalog/movies/" + sharedMovieId).contentType(APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

//		Covert the response body to a MovieResponse object
		MovieResponse movieResponse = objectMapper.readValue(response.getResponse().getContentAsString(), new MovieTypeReference());

		MovieRequest movieRequest = getMovieRequest();

//		Assert that the movie values are the same as the movieRequest values
		Assertions.assertEquals(sharedMovieId, movieResponse.id());
		Assertions.assertEquals(movieRequest.title(), movieResponse.title());
		Assertions.assertEquals(movieRequest.description(), movieResponse.description());
		Assertions.assertEquals(movieRequest.imageUrl(), movieResponse.imageUrl());
		Assertions.assertEquals(movieRequest.cast(), movieResponse.cast());
		Assertions.assertEquals(movieRequest.director(), movieResponse.director());
//		Assertions.assertEquals(movieRequest.releaseDate(), movieResponse.releaseDate());
		Assertions.assertEquals(movieRequest.durationMinutes(), movieResponse.durationMinutes());
		Assertions.assertEquals(movieRequest.genres().size(), movieResponse.genres().size());
	}

	@Test
	@Order(7)
	void shouldGetAllMovies() throws Exception {
//		Perform a GET request to the /api/v1/catalog/movies endpoint
		MvcResult response = mockMvc.perform(get("/api/v1/catalog/movies").contentType(APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

//		Covert the response body to a List of MovieResponse objects
		List<MovieResponse> movieResponses = objectMapper.readValue(response.getResponse().getContentAsString(), new MovieListTypeReference());

		Assertions.assertEquals(1, movieResponses.size());
	}

	@Test
	@Order(8)
	void shouldDeleteMovieById() throws Exception {
//		Perform a DELETE request to the /api/v1/catalog/movies/{id} endpoint
		mockMvc.perform(delete("/api/v1/catalog/movies/" + sharedMovieId).contentType(APPLICATION_JSON)).andExpect(status().isOk());

//		Assert that the movie was deleted successfully
		Assertions.assertEquals(0, movieRepository.findAll().size());
	}

	@Test
	@Order(9)
	void shouldDeleteGenreById() throws Exception {
//		Perform a DELETE request to the /api/v1/catalog/genres/{id} endpoint
		mockMvc.perform(delete("/api/v1/catalog/genres/" + sharedGenreId).contentType(APPLICATION_JSON)).andExpect(status().isOk());

//		Assert that the genre was deleted successfully
		Assertions.assertEquals(0, genreRepository.findAll().size());
	}

	private static class GenreTypeReference extends TypeReference<GenreResponse> { }

	private static class GenreListTypeReference extends TypeReference<List<GenreResponse>> { }

	private static class MovieTypeReference extends TypeReference<MovieResponse> {}

	private static class MovieListTypeReference extends TypeReference<List<MovieResponse>> {}

	private static GenreRequest getGenreRequest() {
		return GenreRequest.builder()
				.name("Action")
				.description("Action movies")
				.imageUrl("https://image.com")
				.build();
	}

	private static GenreRequest getGenreRequestTwo() {
		return GenreRequest.builder()
				.name("Comedy")
				.description("Comedy movies")
				.imageUrl("https://image2.com")
				.build();
	}

	private static MovieRequest getMovieRequest() {
		return MovieRequest.builder()
				.title("The Avengers")
				.description("A group of superheroes save the world")
				.imageUrl("https://image.com")
				.genres(List.of(sharedGenreId))
				.cast(List.of("Robert Downey Jr", "Chris Evans"))
				.director("Joss Whedon")
				.releaseDate("12/12/2012")
				.durationMinutes(120)
				.build();
	}

	private static MovieRequest getMovieRequestTwo() {
		return MovieRequest.builder()
				.title("The Avengers 2")
				.description("A group of superheroes save the world again")
				.imageUrl("https://image2.com")
				.genres(List.of(sharedGenreId))
				.cast(List.of("Robert Downey Jr", "Chris Evans"))
				.director("Joss Whedon")
				.releaseDate("12/12/2014")
				.durationMinutes(130)
				.build();
	}

}
