package org.demo.integration;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ApplicationTests extends BaseIntegrationTests {

	private RestTemplate template = new TestRestTemplate();

	@Test
	public void homePageLoads() {
		ResponseEntity<String> response = template.getForEntity(baseUrl + "/", String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void userEndpointProtected() {
		ResponseEntity<String> response = template.getForEntity(baseUrl + "/api/security/user", String.class);
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
	}

	@Test
	public void resourceEndpointProtected() {
		ResponseEntity<String> response = template.getForEntity(baseUrl + "/api/resource", String.class);
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
	}

	@Test
	public void loginSucceeds() {
		RestTemplate template = new TestRestTemplate("user", "password");
		ResponseEntity<String> response = template.getForEntity(baseUrl + "/api/security/user", String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void resourceEndpointSucceeds() {
		RestTemplate template = new TestRestTemplate("user", "password");
		ResponseEntity<String> response = template.getForEntity(baseUrl + "/api/resource", String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

}
