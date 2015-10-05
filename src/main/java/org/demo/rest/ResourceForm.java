/**
 *
 */
package org.demo.rest;

public class ResourceForm {

	String name;

	/** */
	public ResourceForm() {
	}

	/** */
	public ResourceForm(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
