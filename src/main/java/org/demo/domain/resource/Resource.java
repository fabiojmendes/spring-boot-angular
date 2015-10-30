package org.demo.domain.resource;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.demo.domain.DomainEvent;
import org.demo.domain.DomainEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
public class Resource {

	@Id @Column(length = 16)
	private UUID id;

	@Column(name = "key_", length = 40, nullable = false)
	private String key;

	private String name;

	@Autowired
	transient private DomainEventPublisher eventPublisher;

	@Deprecated
	public Resource() {}

	public Resource(UUID id, DomainEventPublisher eventPublisher) {
		this.id = id;
		this.eventPublisher = eventPublisher;
	}

	/**
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		eventPublisher.publish(new DomainEvent("new key generated"));
		this.key = key;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
