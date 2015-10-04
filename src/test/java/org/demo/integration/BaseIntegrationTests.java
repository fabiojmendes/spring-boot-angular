package org.demo.integration;

import org.demo.Application;
import org.demo.domain.resource.Resource;
import org.demo.domain.resource.ResourceGenerator;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author Fabio Mendes <fabio.mendes@navita.com.br> Oct 4, 2015
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {
	Application.class, BaseIntegrationTests.TestData.class
})
@WebAppConfiguration
@IntegrationTest({
	"server.port:0",
	"spring.datasource.url: jdbc:derby:target/test-db;create=true"
})
public abstract class BaseIntegrationTests {

	@Value("http://localhost:${local.server.port}")
	protected String baseUrl;

	@Configuration
	public static class TestData {

		public static Resource R1;
		public static Resource R2;
		public static Resource R3;

		@Bean InitializingBean initTestData(ResourceGenerator generator) {
			return () -> {
				R1 = generator.generate("R1");
				R2 = generator.generate("R2");
				R3 = generator.generate("R3");
			};
		}

	}
}
