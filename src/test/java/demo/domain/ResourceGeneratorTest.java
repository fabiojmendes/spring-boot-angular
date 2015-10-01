package demo.domain;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class ResourceGeneratorTest {

	@Test
	public void testGenerate() {
		ResourceGenerator generator = new ResourceGenerator();
		String result = generator.generate();
		assertThat(result, notNullValue());
		assertThat(result.length(), is(40));
	}

}
