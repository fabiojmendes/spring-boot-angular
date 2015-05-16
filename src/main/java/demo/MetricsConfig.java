package demo;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.servlet.Filter;
import javax.servlet.ServletContextListener;

import org.coursera.metrics.datadog.DatadogReporter;
import org.coursera.metrics.datadog.transport.HttpTransport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.servlet.InstrumentedFilter;
import com.codahale.metrics.servlet.InstrumentedFilterContextListener;

@Configuration
public class MetricsConfig {

	@Bean
	public ServletContextListener webMetricListener(final MetricRegistry registry) {
		return new InstrumentedFilterContextListener() {
			@Override
			protected MetricRegistry getMetricRegistry() {
				return registry;
			}
		};
	}

	@Bean
	public Filter webMetricFilter() {
		return new InstrumentedFilter();
	}

	@Configuration
	@Profile("production")
	static class DatadogMetricConfig {

		@Value("${HOSTNAME}")
		private String hostname;

		@Value("${APP_NAME}")
		private String appName;

		@Value("${DATADOG_API_KEY}")
		private String datadogApiKey;

		@Bean
		public DatadogReporter datadogReporter(MetricRegistry registry) {
			HttpTransport httpTransport = new HttpTransport.Builder().withApiKey(datadogApiKey).build();
			DatadogReporter reporter = DatadogReporter.forRegistry(registry)
					.withHost(hostname)
					.withTags(Arrays.asList(appName))
					.withTransport(httpTransport)
					.build();
			reporter.start(5, TimeUnit.SECONDS);
			return reporter;
		}

	}
}
