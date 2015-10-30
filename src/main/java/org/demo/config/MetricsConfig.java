package org.demo.config;

import javax.servlet.Filter;
import javax.servlet.ServletContextListener;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.servlet.InstrumentedFilter;
import com.codahale.metrics.servlet.InstrumentedFilterContextListener;

@Configuration
public class MetricsConfig {

	@Bean
	public InitializingBean metricsSetup(final MetricRegistry registry) {
		return () -> {
			registry.registerAll(new GarbageCollectorMetricSet());
			registry.registerAll(new MemoryUsageGaugeSet());
		};
	}

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
	@Profile("!production")
	static class JmxMetricConfig {
		@Bean
		public JmxReporter jmxReporter(MetricRegistry registry) {
			JmxReporter reporter = JmxReporter.forRegistry(registry).build();
			reporter.start();
			return reporter;
		}
	}
}
