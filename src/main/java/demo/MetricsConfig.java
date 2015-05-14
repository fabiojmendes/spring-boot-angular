package demo;

import java.util.concurrent.TimeUnit;

import javax.servlet.Filter;
import javax.servlet.ServletContextListener;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.servlet.InstrumentedFilter;
import com.codahale.metrics.servlet.InstrumentedFilterContextListener;

@Configuration
public class MetricsConfig {

	//@Bean
	public ConsoleReporter consoleReporter(MetricRegistry registry) {
		ConsoleReporter reporter = ConsoleReporter.forRegistry(registry)
				.convertRatesTo(TimeUnit.SECONDS)
				.convertDurationsTo(TimeUnit.MILLISECONDS)
				.build();
		reporter.start(5, TimeUnit.SECONDS);
		return reporter;
	}

	@Bean
	public JmxReporter jmxReporter(MetricRegistry registry) {
		registry.registerAll(new GarbageCollectorMetricSet());
		registry.registerAll(new MemoryUsageGaugeSet());
		JmxReporter reporter = JmxReporter.forRegistry(registry).build();
		reporter.start();
		return reporter;
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
}