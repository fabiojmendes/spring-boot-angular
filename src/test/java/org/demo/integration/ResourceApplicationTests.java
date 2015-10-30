package org.demo.integration;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.UUID;

import org.demo.rest.ResourceForm;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ResourceApplicationTests extends BaseIntegrationTests {

	private RestTemplate template = new TestRestTemplate("user", "password");

	private final String testName = "Test Name";

	static class ResourceJson {
		public UUID id;
		public String name;
		public String key;
	}

	@Test
	public void testAddOne() {
		ResponseEntity<ResourceJson> response = template.postForEntity(baseUrl + "/api/resource", new ResourceForm(testName), ResourceJson.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		ResourceJson testResource = response.getBody();
		assertThat(testResource.id, notNullValue());
		assertThat(testResource.name, is(testName));
		assertThat(testResource.key.length(), is(40));
	}

	@Test
	public void testGetOne() {
		ResponseEntity<ResourceJson> response = template.getForEntity(baseUrl + "/api/resource/{id}", ResourceJson.class, TestData.R1.getId());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		ResourceJson responseResource = response.getBody();
		assertThat(responseResource.id, is(TestData.R1.getId()));
		assertThat(responseResource.name, is(TestData.R1.getName()));
		assertThat(responseResource.key, is(TestData.R1.getKey()));
	}

	@Test
	public void testUpdate() {
		template.put(baseUrl + "/api/resource/{id}", new ResourceForm(testName), TestData.R2.getId());
		ResponseEntity<ResourceJson> response = template.getForEntity(baseUrl + "/api/resource/{id}", ResourceJson.class, TestData.R2.getId());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		ResourceJson responseResource = response.getBody();
		assertThat(responseResource.id, is(TestData.R2.getId()));
		assertThat(responseResource.name, is(testName));
		assertThat(responseResource.key, is(TestData.R2.getKey()));
	}


	@Test
	public void testInvalidUpdate() {
		HttpEntity<ResourceForm> payload = new HttpEntity<>(new ResourceForm(testName));
		ResponseEntity<String> response = template.exchange(baseUrl + "/api/resource/{id}", HttpMethod.PUT, payload, String.class, UUID.randomUUID());
		assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
	}

	@Test
	public void testDelete() {
		template.delete(baseUrl + "/api/resource/{id}", TestData.R3.getId());
		ResponseEntity<String> response = template.getForEntity(baseUrl + "/api/resource/{id}", String.class, TestData.R3.getId());
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}


	@Test
	public void testInvalidDelete() {
		ResponseEntity<String> response = template.exchange(baseUrl + "/api/resource/{id}", HttpMethod.DELETE, null, String.class, UUID.randomUUID());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
}
