/**
 *
 */
package org.demo.domain;

import java.time.Instant;

/**
 * @author Fabio Mendes <fabio.mendes@navita.com.br> Oct 30, 2015
 *
 */
public class DomainEvent {

	private String name;

	private Instant instant;

	/**
	 * @param name
	 */
	public DomainEvent(String name) {
		this.name = name;
		this.instant = Instant.now();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the instant
	 */
	public Instant getInstant() {
		return instant;
	}
}
