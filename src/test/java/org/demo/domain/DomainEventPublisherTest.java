package org.demo.domain;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Test;

/**
 * @author Fabio Mendes <fabio.mendes@navita.com.br> Oct 30, 2015
 *
 */
public class DomainEventPublisherTest {

	private DomainEventPublisher domainEventPublisher = new DomainEventPublisher();

	@Test
	public void testPublishEvent() {
		DomainEvent event = new DomainEvent("test");
		domainEventPublisher.publish(event);
		assertThat(domainEventPublisher.getPublishedEvents(), hasItem(event));
		Optional<DomainEvent> optEvent = domainEventPublisher.getPublishedEvents().stream().findFirst();
		assertThat(optEvent.isPresent(), is(true));
		assertThat(optEvent.get().getName(), is(event.getName()));
		assertThat(optEvent.get().getInstant(), is(event.getInstant()));
	}

}
