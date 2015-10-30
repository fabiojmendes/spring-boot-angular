/**
 *
 */
package org.demo.domain;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Component;

/**
 * @author Fabio Mendes <fabio.mendes@navita.com.br> Oct 30, 2015
 *
 */
@Component
public class DomainEventPublisher {

	private Collection<DomainEvent> publishedEvents;

	/** */
	public DomainEventPublisher() {
		publishedEvents = new ArrayList<>();
	}

	public void publish(DomainEvent event) {
		publishedEvents.add(event);
	}

	/**
	 * @return
	 */
	public Collection<DomainEvent> getPublishedEvents() {
		return publishedEvents;
	}

}
