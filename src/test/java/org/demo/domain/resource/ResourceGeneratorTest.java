package org.demo.domain.resource;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import org.demo.domain.resource.Resource;
import org.demo.domain.resource.ResourceGenerator;
import org.demo.domain.resource.ResourceRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ResourceGeneratorTest {

	private static final String NAME = "Test Name";

	@Mock
	private ResourceRepository resourceRepositoryMock;

	private Resource testResource;

	private ResourceGenerator generator;

	@Before
	public void setup() {
		testResource = new Resource(UUID.randomUUID());
		testResource.setKey("1234");
		testResource.setName(NAME);
		generator = new ResourceGenerator(resourceRepositoryMock);
	}

	@Test
	public void testGenerate() {
		when(resourceRepositoryMock.save(Matchers.any(Resource.class))).thenAnswer(invocation -> {
			return invocation.getArgumentAt(0, Resource.class);
		});
		Resource result = generator.generate(NAME);
		assertThat(result, notNullValue());
		assertThat(result.getKey().length(), is(40));
		assertThat(result.getName(), is(NAME));
		verify(resourceRepositoryMock).save(Matchers.any(Resource.class));
	}

	@Test
	public void testUpdate() {
		when(resourceRepositoryMock.findById(testResource.getId())).thenReturn(Optional.of(testResource));
		generator.update(testResource.getId(), "New Name");
		assertThat(testResource.getName(), is("New Name"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUpdateWithInvalidId() {
		when(resourceRepositoryMock.findById(anyObject())).thenReturn(Optional.empty());
		generator.update(UUID.randomUUID(), "New Name");
	}

	@Test
	public void testDelete() {
		generator.delete(testResource.getId());
		verify(resourceRepositoryMock).delete(testResource.getId());
	}

}
