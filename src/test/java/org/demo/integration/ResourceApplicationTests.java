package org.demo.integration;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.UUID;

import org.demo.domain.resource.Resource;
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

	@Test
	public void testAddOne() {
		ResponseEntity<Resource> response = template.postForEntity(baseUrl + "/resource", new ResourceForm(testName), Resource.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		Resource testResource = response.getBody();
		assertThat(testResource.getId(), notNullValue());
		assertThat(testResource.getName(), is(testName));
		assertThat(testResource.getKey().length(), is(40));
	}

	@Test
	public void testGetOne() {
		ResponseEntity<Resource> response = template.getForEntity(baseUrl + "/resource/{id}", Resource.class, TestData.R1.getId());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		Resource responseResource = response.getBody();
		assertThat(responseResource.getId(), is(TestData.R1.getId()));
		assertThat(responseResource.getName(), is(TestData.R1.getName()));
		assertThat(responseResource.getKey(), is(TestData.R1.getKey()));
	}

	@Test
	public void testUpdate() {
		template.put(baseUrl + "/resource/{id}", new ResourceForm(testName), TestData.R2.getId());
		ResponseEntity<Resource> response = template.getForEntity(baseUrl + "/resource/{id}", Resource.class, TestData.R2.getId());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		Resource responseResource = response.getBody();
		assertThat(responseResource.getId(), is(TestData.R2.getId()));
		assertThat(responseResource.getName(), is(testName));
		assertThat(responseResource.getKey(), is(TestData.R2.getKey()));
	}


	@Test
	public void testInvalidUpdate() {
		HttpEntity<ResourceForm> payload = new HttpEntity<>(new ResourceForm(testName));
		ResponseEntity<String> response = template.exchange(baseUrl + "/resource/{id}", HttpMethod.PUT, payload, String.class, UUID.randomUUID());
		assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
	}

	@Test
	public void testDelete() {
		template.delete(baseUrl + "/resource/{id}", TestData.R3.getId());
		ResponseEntity<String> response = template.getForEntity(baseUrl + "/resource/{id}", String.class, TestData.R3.getId());
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}


	@Test
	public void testInvalidDelete() {
		ResponseEntity<String> response = template.exchange(baseUrl + "/resource/{id}", HttpMethod.DELETE, null, String.class, UUID.randomUUID());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
}
