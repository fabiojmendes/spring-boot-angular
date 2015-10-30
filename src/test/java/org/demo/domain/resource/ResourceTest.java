/**
 *
 */
package org.demo.domain.resource;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.demo.domain.DomainEvent;
import org.demo.domain.DomainEventPublisher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Fabio Mendes <fabio.mendes@navita.com.br> Oct 30, 2015
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ResourceTest {

	@Mock
	private DomainEventPublisher eventPublisherMock;

	@Test
	public void testEventPublished() {
		Resource res = new Resource(UUID.randomUUID(), eventPublisherMock);
		res.setKey("lala");

		verify(eventPublisherMock).publish(isA(DomainEvent.class));
	}

}
