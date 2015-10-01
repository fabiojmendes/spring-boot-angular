package demo.domain;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ResourceGeneratorTest {
	
	@Mock
	private ResourceRepository resourceRepositoryMock;
	
	private Resource testResource;
	
	@Before
	public void setup() {
		testResource = new Resource(UUID.randomUUID());
		testResource.setKey("1234");
	}

	@Test
	public void testGenerate() {
		when(resourceRepositoryMock.save(Matchers.any(Resource.class))).thenAnswer(invocation -> {
			return invocation.getArgumentAt(0, Resource.class);
		});
		ResourceGenerator generator = new ResourceGenerator(resourceRepositoryMock);
		Resource result = generator.generate();
		assertThat(result, notNullValue());
		assertThat(result.getKey().length(), is(40));
		verify(resourceRepositoryMock).save(Matchers.any(Resource.class));
	}

}
