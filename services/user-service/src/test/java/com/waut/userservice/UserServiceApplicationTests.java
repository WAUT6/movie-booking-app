package com.waut.userservice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waut.userservice.dto.UserRequest;
import com.waut.userservice.dto.UserResponse;
import com.waut.userservice.model.User;
import com.waut.userservice.repository.UserRepository;
import junit.framework.AssertionFailedError;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.MethodOrderer.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class UserServiceApplicationTests {

	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UserRepository userRepository;

	private static String sharedUserId;

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Test
	@Order(1)
	void shouldCreateUser() throws Exception {
		UserRequest userRequest = getUserRequest();

//		Convert the userRequest object to a JSON string
		String userRequestString = objectMapper.writeValueAsString(userRequest);

//		Perform a POST request to the /api/v1/users endpoint
		MvcResult response = mockMvc.perform(
				post("/api/v1/users")
						.contentType(APPLICATION_JSON)
						.content(userRequestString)
				)
				.andExpect(status().isCreated())
				.andReturn();

//		Assert that the user was saved successfully
		Assertions.assertEquals(1, userRepository.findAll().size());

//		Assert that the response body is the same as the user ID
		User user = userRepository.findAll().get(0);
		String responseBody = response.getResponse().getContentAsString();
		sharedUserId = responseBody;
//		Assert that the saved user values are the same as the userRequest values
		Assertions.assertEquals(user.getId(), responseBody);
		Assertions.assertEquals(user.getFirstName(), userRequest.firstName());
		Assertions.assertEquals(user.getLastName(), userRequest.lastName());
		Assertions.assertEquals(user.getEmail(), userRequest.email());
		Assertions.assertEquals(user.getUserName(), userRequest.userName());
		Assertions.assertEquals(user.getPhoneNumber(), userRequest.phoneNumber());
	}

	@Test
	@Order(2)
    public void shouldGetUserById() throws Exception {
//		Perform a GET request to the /api/v1/users/{id} endpoint
		MvcResult response = mockMvc.perform(
				MockMvcRequestBuilders.get("/api/v1/users/" + sharedUserId)
						.contentType(APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andReturn();

//		 Assert that the response body is the same as the user ID
		String responseBody = response.getResponse().getContentAsString();

//		Covert the response body to a UserResponse object
		UserResponse userResponse = objectMapper.readValue(responseBody, new UserTypeReference());

		UserRequest userRequest = getUserRequest();

//		Assert that the user values are the same as the userRequest values
		Assertions.assertEquals(sharedUserId, userResponse.id());
		Assertions.assertEquals(userRequest.firstName(), userResponse.firstName());
		Assertions.assertEquals(userRequest.lastName(), userResponse.lastName());
		Assertions.assertEquals(userRequest.email(), userResponse.email());
		Assertions.assertEquals(userRequest.userName(), userResponse.userName());
		Assertions.assertEquals(userRequest.phoneNumber(), userResponse.phoneNumber());
	}

	@Test
	@Order(3)
	public void shouldUpdateUserById() throws Exception {
		UserRequest userRequest = getUserRequestTwo();

//		Convert the userRequest object to a JSON string
		String userRequestString = objectMapper.writeValueAsString(userRequest);

//		Perform a PUT request to the /api/v1/users/{id} endpoint
		mockMvc.perform(
				MockMvcRequestBuilders.put("/api/v1/users/" + sharedUserId)
						.contentType(APPLICATION_JSON)
						.content(userRequestString)
				)
				.andExpect(status().isAccepted());

//		Assert that the user was updated successfully
		User user = userRepository.findById(sharedUserId).orElseThrow(() -> new AssertionFailedError("User not found"));

//		Assert that the updated user values are the same as the userRequest values
		Assertions.assertEquals(user.getFirstName(), userRequest.firstName());
		Assertions.assertEquals(user.getLastName(), userRequest.lastName());
		Assertions.assertEquals(user.getEmail(), userRequest.email());
		Assertions.assertEquals(user.getPhoneNumber(), userRequest.phoneNumber());
	}

	@Test
	@Order(4)
	public void shouldDeleteUserById() throws Exception {
//		Perform a DELETE request to the /api/v1/users/{id} endpoint
		mockMvc.perform(
				MockMvcRequestBuilders.delete("/api/v1/users/" + sharedUserId)
						.contentType(APPLICATION_JSON)
				)
				.andExpect(status().isOk());

//		Assert that the user was deleted successfully
		Assertions.assertEquals(0, userRepository.findAll().size());
	}

	private static class UserTypeReference extends TypeReference<UserResponse> {
	}

	private UserRequest getUserRequest() {
		return UserRequest.builder()
				.firstName("John")
				.lastName("Doe")
				.email("johndoe@gmail.com")
				.userName("johndoe")
				.password("Password1")
				.phoneNumber("1234567890")
				.build();
	}

	private UserRequest getUserRequestTwo() {
		return UserRequest.builder()
				.firstName("Jane")
				.lastName("Doe")
				.email("janedoe@gmail.com")
				.userName("janedoe")
				.password("Password1")
				.phoneNumber("1334567890")
				.build();
	}

}
